package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dto.StartupList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LaunchpadParserService
{
    private static final String URL = "http://launchpad.espooinnovationgarden.fi/";


    public LaunchpadParserService()
    {
    }


    public void fetchData()
    {
        RestTemplate restTemplate = new RestTemplate();
        StartupList startupList = restTemplate.getForObject(URL + "_ah/api/company/v1/startups", StartupList.class);
        System.out.println(startupList.getItems().size());
    }
}
