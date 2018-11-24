package com.freemahn.smartbridge;

import com.freemahn.smartbridge.repository.StartupRepository;
import com.freemahn.smartbridge.service.LaunchpadParserService;
import com.freemahn.smartbridge.service.MattermarkParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner
{

    private LaunchpadParserService launchpadParserService;
    private MattermarkParserService mattermarkParserService;


    public DataLoader(LaunchpadParserService launchpadParserService, MattermarkParserService mattermarkParserService)
    {
        this.launchpadParserService = launchpadParserService;
        this.mattermarkParserService = mattermarkParserService;
    }


    @Override
    public void run(String... args) throws Exception
    {
        //if required
        launchpadParserService.fetchStartupData();
        launchpadParserService.fetchCorporatesData();

        mattermarkParserService.ehnanceStartups();


    }
}