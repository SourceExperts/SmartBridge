package com.freemahn.smartbridge;

import com.freemahn.smartbridge.service.LaunchpadParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner
{

    private LaunchpadParserService launchpadParserService;


    public DataLoader(LaunchpadParserService launchpadParserService)
    {
        this.launchpadParserService = launchpadParserService;
    }


    @Override
    public void run(String... args) throws Exception
    {

        launchpadParserService.fetchData();
    }
}