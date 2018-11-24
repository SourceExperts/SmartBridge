package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dao.match.Bridge;
import com.freemahn.smartbridge.repository.BridgeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class BridgeService
{
    private final BridgeRepository bridgeRepository;


    public BridgeService(BridgeRepository bridgeRepository)
    {
        this.bridgeRepository = bridgeRepository;
    }


    /**
     * Finds all prevous 'similar' matches
     * in current implementation 'similar' means companies with same industries
     *
     * @param companyPreferableOptions
     * @return
     */

    public List<Bridge> findByBridgeRequest(CompanyPreferableOptions companyPreferableOptions)
    {
        List<Bridge> all = bridgeRepository.findAll();
        all = all.stream().filter(bridge -> {
            boolean matchIndustry = bridge.getCorporate().getIndustries().contains(companyPreferableOptions.getIndustry());
            return matchIndustry;
        }).collect(Collectors.toList());
        return all;
    }
}
