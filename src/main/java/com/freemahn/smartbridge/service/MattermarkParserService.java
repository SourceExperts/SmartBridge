package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.CompanyMetadata;
import com.freemahn.smartbridge.dao.CompanyMetadataDTO;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.repository.StartupRepository;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MattermarkParserService
{
    private final StartupRepository startupRepository;
    private String key1 = "a067754b616182d7f8d41041d245c371be3483c6edf2872845f420b8bd0aeb61";
    private String key2 = "a067754b616182d7f8d41041d245c371be3483c6edf2872845f420b8bd0aeb61";

    public MattermarkParserService(StartupRepository startupRepository)
    {
        this.startupRepository = startupRepository;
    }


    public void findSimilar(Startup startup, String key)
    {
        RestTemplate restTemplate = new RestTemplate();

        String term = startup.getName();
        ResponseEntity<List<CompanyMetadataDTO>> resp = restTemplate.exchange("https://api.mattermark.com/search?key=" + key + "&term=" + term,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<CompanyMetadataDTO>>()
            {
            });
        List<CompanyMetadataDTO> result = resp.getBody();
        if (result == null || result.isEmpty())
        {
            log.info("No metadata for startup {}", startup.getName());
            return;
        }
        else
        {
            log.info("{} results for startup {}", result.size(), startup.getName());
        }
        List<CompanyMetadata> collect = result.stream().map(CompanyMetadata::new).collect(Collectors.toList());
        CompanyMetadata companyMetadata = collect.get(0);
        startup.setCompanyMetadata(companyMetadata);
        startupRepository.save(startup);

    }


    public void ehnanceStartups()
    {
        List<Startup> startups = startupRepository.findAll();
        List<List<Startup>> parts = Lists.partition(startups, 40);
        parts.get(0).forEach(startup -> {
            findSimilar(startup, key1);
        });
        parts.get(1).forEach(startup -> {
            findSimilar(startup, key2);
        });

    }
}
