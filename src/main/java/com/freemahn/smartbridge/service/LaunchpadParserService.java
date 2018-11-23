package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dto.StartupList;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LaunchpadParserService
{
    private static final String URL = "http://launchpad.espooinnovationgarden.fi/";

    private StartupRepository startupRepository;


    public LaunchpadParserService(StartupRepository startupRepository)
    {
        this.startupRepository = startupRepository;
    }


    public void fetchData()
    {
        RestTemplate restTemplate = new RestTemplate();
        StartupList startupList = restTemplate.getForObject(URL + "_ah/api/company/v1/startups", StartupList.class);

        List<Startup> startupDAOList = new ArrayList<>();
        startupList.getItems().forEach(s -> {

                startupDAOList.add(new Startup(s));
            }
        );
        startupRepository.saveAll(startupDAOList);
        startupRepository.findAll().forEach(startup -> log.info("{}", startup));
    }
}
