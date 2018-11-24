package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dto.CorporateList;
import com.freemahn.smartbridge.dto.StartupDTO;
import com.freemahn.smartbridge.dto.StartupList;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        startupList.forEach(startup -> startupRepository.findById(startup.getId()).orElse(startupRepository.save(startup)));
    }


    @Transactional
    public void fetchStartupLogos()
    {
        RestTemplate restTemplate = new RestTemplate();
        StartupList startupDTOList = restTemplate.getForObject(URL + "_ah/api/company/v1/startups", StartupList.class);
        Map<String, StartupDTO> map = startupDTOList.getItems().stream().collect(Collectors.toMap(StartupDTO::getId, x -> x));
        List<Startup> startupList = startupRepository.findAll();
        startupList.forEach(startup -> {
            if (startup.getLogo() == null)
            {
                StartupDTO startupDTO = map.get(String.valueOf(startup.getId()));
                startup.setLogo(startupDTO.getLogo());
            }
        });

        Objects.requireNonNull(startupDTOList).getItems().forEach(s -> startupList.add(new Startup(s)));
        startupRepository.saveAll(startupList);
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
    }
}
