package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dto.CorporateList;
import com.freemahn.smartbridge.dto.StartupList;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LaunchpadParserService
{
    private static final String URL = "http://launchpad.espooinnovationgarden.fi/";

    private StartupRepository startupRepository;
    private CorporateRepository corporateRepository;


    public LaunchpadParserService(StartupRepository startupRepository, CorporateRepository corporateRepository)
    {
        this.startupRepository = startupRepository;
        this.corporateRepository = corporateRepository;
    }


    public void fetchStartupData()
    {
        RestTemplate restTemplate = new RestTemplate();
        StartupList startupList = restTemplate.getForObject(URL + "_ah/api/company/v1/startups", StartupList.class);

        List<Startup> startupDAOList = new ArrayList<>();
        Objects.requireNonNull(startupList).getItems().forEach(s -> startupDAOList.add(new Startup(s)));
        startupRepository.saveAll(startupDAOList);
        startupRepository.findAll().forEach(startup -> log.info("{}", startup));
    }


    public void fetchCorporatesData()
    {
        RestTemplate restTemplate = new RestTemplate();
        CorporateList corporateList = restTemplate.getForObject(URL + "_ah/api/company/v1/corporates", CorporateList.class);

        List<Corporate> corporates = new ArrayList<>();
        Objects.requireNonNull(corporateList).getItems().forEach(s -> corporates.add(new Corporate(s)));
        corporateRepository.saveAll(corporates);
        corporateRepository.findAll().forEach(corporate -> log.info("{}", corporate));
    }
}
