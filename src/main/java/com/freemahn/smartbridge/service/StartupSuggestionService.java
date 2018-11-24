package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.controller.CorporateController;
import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dao.match.Bridge;
import com.freemahn.smartbridge.repository.BridgeRepository;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StartupSuggestionService
{

    private final CorporateRepository corporateRepository;
    private final StartupRepository startupRepository;
    private final BridgeRepository bridgeRepository;
    private final BridgeService bridgeService;


    public StartupSuggestionService(
        CorporateRepository corporateRepository,
        StartupRepository startupRepository,
        BridgeRepository bridgeRepository, BridgeService bridgeService)
    {
        this.corporateRepository = corporateRepository;
        this.startupRepository = startupRepository;
        this.bridgeRepository = bridgeRepository;
        this.bridgeService = bridgeService;
    }


    //we need transaction annotation cause of lob description property
    @Transactional
    public List<Startup> doSuggestionMagic(long companyId)
    {
        Corporate corporate = corporateRepository.getOne(companyId);
        CompanyPreferableOptions companyPreferableOptions = corporate.getAccount();
        List<Startup> startups = startupRepository.findAll();

        startups = startups.stream().filter(startup -> {

                boolean industriesEqual = startup.getIndustries().stream().map(String::toLowerCase).collect(Collectors.toList())
                    .contains(companyPreferableOptions.getIndustry().toLowerCase());
                boolean cityEqual = startup.getCity().equalsIgnoreCase(companyPreferableOptions.getLocation());

                if (!industriesEqual)
                {
                    log.info("industries {}/{}", companyPreferableOptions.getIndustry(), startup.getIndustries());
                }
                if (!cityEqual)
                {
                    log.info("city {}/{}", companyPreferableOptions.getLocation(), startup.getCity());
                }

                log.info("Startup {}, industries {}, city {}", startup.getName(), industriesEqual, cityEqual);
                log.info("===================================");
                return industriesEqual && cityEqual;
            }

        ).collect(Collectors.toList());

        //        Bridge matchHistory = Bridge.builder()
        //            .companyPreferableOptions(companyPreferableOptions)
        //            .matchedStartups(startups)
        //            .corporate(corporate)
        //            .build();
        //        bridgeRepository.save(matchHistory);

        return startups;

    }


    /**
     * Match-based search
     *
     * @param companyId
     * @return
     */
    @Transactional
    public List<Startup> doExploreMagic(long companyId)
    {
        CompanyPreferableOptions defaultCorporateCompanyPreferableOptions = corporateRepository.getOne(companyId).getAccount();
        List<Bridge> previousMatches = bridgeService.findByBridgeRequest(defaultCorporateCompanyPreferableOptions);
        return previousMatches.stream().map(Bridge::getMatchedStartup).collect(Collectors.toList());
    }


    public void createBridge(long corporateId, CorporateController.Payload payload)
    {
        Corporate corporate = corporateRepository.getOne(corporateId);
        Startup startup = startupRepository.getOne(payload.getStartupId());
        Bridge bridge = Bridge.builder()
            .corporate(corporate)
            .matchedStartup(startup)
            .name(payload.getName())
            .description(payload.getDescription())
            .build();
        bridgeRepository.save(bridge);
        log.info("Bridge {} created. Matched company {} with startup {}", payload.getName(),
            corporate, startup);
    }


    public void fetchBridges(long corporateId)
    {
        bridgeRepository.findAllByCorporate(corporateRepository.getOne(corporateId));
    }
}
