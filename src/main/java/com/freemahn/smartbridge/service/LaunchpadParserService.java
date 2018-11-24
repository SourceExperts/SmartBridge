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
    private final MattermarkParserService mattermarkParserService;


    public LaunchpadParserService(
        StartupRepository startupRepository,
        CorporateRepository corporateRepository,
        MattermarkParserService mattermarkParserService)
    {
        this.startupRepository = startupRepository;
        this.corporateRepository = corporateRepository;
        this.mattermarkParserService = mattermarkParserService;
    }


    public void fetchStartupData()
    {
        if (!startupRepository.findAll().isEmpty())
        {
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        StartupList startupDTOList = restTemplate.getForObject(URL + "_ah/api/company/v1/startups", StartupList.class);

        List<Startup> startupList = new ArrayList<>();
        Objects.requireNonNull(startupDTOList).getItems().forEach(s -> startupList.add(new Startup(s)));
        //save only new ones
        startupList.removeIf(x -> {
            return startupRepository.existsById(x.getId());
        });
        startupRepository.saveAll(startupList);
//        startupRepository.findAll().forEach(startup -> log.info("{}", startup));
    }


    public void fetchCorporatesData()
    {
        if (!corporateRepository.findAll().isEmpty())
        {
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        CorporateList corporateList = restTemplate.getForObject(URL + "_ah/api/company/v1/corporates", CorporateList.class);

        List<Corporate> corporates = new ArrayList<>();
        Objects.requireNonNull(corporateList).getItems().forEach(s -> corporates.add(new Corporate(s)));
        corporateRepository.saveAll(corporates);
//        corporateRepository.findAll().forEach(corporate -> log.info("{}", corporate));
    }
}
