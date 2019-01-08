package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dao.match.Bridge;
import com.freemahn.smartbridge.repository.BridgeRepository;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.Collections;
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
    private static final int MAX_SIZE = 21;


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
//        log.info("Corporate preferences {}", companyPreferableOptions.getLocation(), );
        List<Startup> startups = startupRepository.findAll();

        List<Startup> filteredBy3Startups = startups.stream().filter(startup -> {
                boolean industriesEqual = startup.getIndustries().stream().map(String::toLowerCase).collect(Collectors.toList())
                    .contains(companyPreferableOptions.getIndustry().toLowerCase());
                boolean cityEqual = startup.getCity().equalsIgnoreCase(companyPreferableOptions.getLocation());
            boolean stageEqual = startup.getInfo().getStage().equalsIgnoreCase(companyPreferableOptions.getStage());

            log.info("Startup {}, industries: {}, city: {}, stage: {}", startup.getName(), industriesEqual, cityEqual, stageEqual);
                log.info("===================================");
            return industriesEqual && cityEqual && stageEqual;
            }

        ).collect(Collectors.toList());

        if (filteredBy3Startups.size() >= MAX_SIZE)
        {
            return filteredBy3Startups.stream().limit(MAX_SIZE).collect(Collectors.toList());
        }

        startups.removeAll(filteredBy3Startups);
        List<Startup> filteredBy2Startups = startups.stream().filter(startup -> {

                boolean industriesEqual = startup.getIndustries().stream().map(String::toLowerCase).collect(Collectors.toList())
                    .contains(companyPreferableOptions.getIndustry().toLowerCase());
                boolean cityEqual = startup.getCity().equalsIgnoreCase(companyPreferableOptions.getLocation());

                log.info("Startup {}, industries: {}, city: {}", startup.getName(), industriesEqual, cityEqual);
                log.info("===================================");
                return industriesEqual && cityEqual;
            }

        ).collect(Collectors.toList());

        filteredBy3Startups.addAll(filteredBy2Startups);

        if (filteredBy3Startups.size() >= MAX_SIZE)
        {
            return filteredBy3Startups.stream().limit(MAX_SIZE).collect(Collectors.toList());
        }
        startups.removeAll(filteredBy2Startups);

        List<Startup> filteredBy1Startups = startups.stream().filter(startup -> {

                boolean industriesEqual = startup.getIndustries().stream().map(String::toLowerCase).collect(Collectors.toList())
                    .contains(companyPreferableOptions.getIndustry().toLowerCase());

                log.info("Startup {}, industries: {}", startup.getName(), industriesEqual);
                log.info("===================================");
                return industriesEqual;
            }

        ).collect(Collectors.toList());

        filteredBy3Startups.addAll(filteredBy1Startups);
        if (filteredBy3Startups.size() >= MAX_SIZE)
        {
            return filteredBy3Startups.stream().limit(MAX_SIZE).collect(Collectors.toList());
        }

        startups.removeAll(filteredBy1Startups);
        startups.sort((x, y) -> {
            if (x.getInfo() != null && y.getInfo() != null)
            {
                if (x.getInfo().getMattermarkScore() != null && y.getInfo().getMattermarkScore() != null)
                {
                    return x.getInfo().getMattermarkScore() - y.getInfo().getMattermarkScore();
                }

                if (x.getInfo().getMattermarkScore() == null)
                {
                    return -1;
                }
                if (y.getInfo().getMattermarkScore() == null)
                {
                    return 1;
                }
            }
            if (x.getInfo() == null)
            {
                return -1;
            }
            if (y.getInfo() == null)
            {
                return 1;
            }
            return 0;
        });
        //        Bridge matchHistory = Bridge.builder()
        //            .companyPreferableOptions(companyPreferableOptions)
        //            .matchedStartups(startups)
        //            .corporate(corporate)
        //            .build();
        //        bridgeRepository.save(matchHistory);

        filteredBy3Startups.addAll(startups);

        return filteredBy3Startups.stream().limit(MAX_SIZE).collect(Collectors.toList());

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
        List<Startup> startups = previousMatches.stream().map(Bridge::getMatchedStartup).collect(Collectors.toList());
        Collections.shuffle(startups);
        return startups.stream().limit(3).collect(Collectors.toList());
    }


}
