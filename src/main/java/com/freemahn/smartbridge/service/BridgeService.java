package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dao.match.Bridge;
import com.freemahn.smartbridge.dto.Payload;
import com.freemahn.smartbridge.repository.BridgeRepository;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
@Slf4j
public class BridgeService
{
    private final BridgeRepository bridgeRepository;
    private final CorporateRepository corporateRepository;
    private final StartupRepository startupRepository;


    public BridgeService(BridgeRepository bridgeRepository, CorporateRepository corporateRepository, StartupRepository startupRepository)
    {
        this.bridgeRepository = bridgeRepository;
        this.corporateRepository = corporateRepository;
        this.startupRepository = startupRepository;
    }


    /**
     *
     * EXPLORE-feature
     * Finds all prevous 'similar' matches
     * in current implementation 'similar' means companies with same industries
     *
     * @param companyPreferableOptions
     * @return
     */

    public List<Bridge> findByBridgeRequest(CompanyPreferableOptions companyPreferableOptions)
    {
        List<Bridge> all = bridgeRepository.findAll();

        log.info("Finding all britches, which corporate has industry {}", companyPreferableOptions.getIndustry());
        all = all.stream().filter(bridge -> {
            boolean matchIndustry = bridge.getCorporate().getIndustries().contains(companyPreferableOptions.getIndustry());
            log.info("bridge name [{}],result {},  industries {}, ", bridge.getName(), matchIndustry, bridge.getCorporate().getIndustries());

            return matchIndustry;
        }).collect(Collectors.toList());
        return all;
    }


    public void createBridge(long corporateId, Payload payload)
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
        startup.setAmountOfBridges(startup.getAmountOfBridges() + 1);
        startupRepository.save(startup);
        log.info("Bridge {} created. Matched company {} with startup {}", payload.getName(),
            corporate, startup);
    }


    public List<Bridge> fetchBridges(long corporateId)
    {
        return bridgeRepository.findAllByCorporate(corporateRepository.getOne(corporateId));
    }
}
