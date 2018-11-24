package com.freemahn.smartbridge.service;

import com.freemahn.smartbridge.dao.CompanyMetadata;
import com.freemahn.smartbridge.dao.CompanyMetadataDTO;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.repository.StartupRepository;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MattermarkParserService
{
    private final StartupRepository startupRepository;
    private String key1 = "f7dea733ddef6142238c0fdf7562830c839b12e36e4f60bd22cbdb9ab326b2dc";
    private String key2 = "54029ab2d46e5d28a4daa319e2ba6257e87841f26a4943b4cf19275724348600";

    public MattermarkParserService(StartupRepository startupRepository)
    {
        this.startupRepository = startupRepository;
    }


    @Transactional
    public void findSimilar(Startup startup, String key, int group)
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
            log.info("group {}. No metadata for startup {}", group, startup.getName());
            startup.setMetadataParsed(true);
            startupRepository.save(startup);
            return;
        }
        else
        {
            log.info("group {}. {} results for startup {}", group, result.size(), startup.getName());
        }
        List<CompanyMetadata> collect = result.stream().map(CompanyMetadata::new).collect(Collectors.toList());
        CompanyMetadata companyMetadata = collect.get(0);
        startup.setMetadataCompanyId(companyMetadata.getObjectId());
        startup.setMetadataParsed(true);
        startupRepository.save(startup);
    }


    @Transactional
    public void ehnanceStartups()
    {
        List<Startup> startups = startupRepository.findAllByMetadataParsed(false);
        if (startups.isEmpty())
        {
            log.info("All startups are enhanced by metadata");
            return;
        }
        List<List<Startup>> parts = Lists.partition(startups, 40);
        AtomicInteger i = new AtomicInteger();
        parts.get(0).forEach(startup -> {
            findSimilar(startup, key1, 0);
            try
            {
                Thread.sleep(new Random().nextInt(800) + 200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
        i.set(0);
        parts.get(1).forEach(startup -> {
            findSimilar(startup, key2, 1);
            try
            {
                Thread.sleep(new Random().nextInt(800) + 200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });

    }

//    @Transactional
//    public void enhanceWithData(Startup startup, String key, int group)
//    {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Long metadataCompanyId = startup.getMetadataCompanyId();
//        ResponseEntity<List<CompanyDataDto>> resp = restTemplate.exchange("https://api.mattermark.com/companies/" +
//                metadataCompanyId + "?key=" + key,
//            HttpMethod.GET, null, new ParameterizedTypeReference<List<CompanyDataDto>>()
//            {
//            });
//        List<CompanyMetadataDTO> result = resp.getBody();
//        if (result == null || result.isEmpty())
//        {
//            log.info("group {}. No metadata for startup {}", group, startup.getName());
//            startup.setMetadataParsed(true);
//            startupRepository.save(startup);
//            return;
//        }
//        else
//        {
//            log.info("group {}. {} results for startup {}", group, result.size(), startup.getName());
//        }
//        List<CompanyMetadata> collect = result.stream().map(CompanyMetadata::new).collect(Collectors.toList());
//        CompanyMetadata companyMetadata = collect.get(0);
//        startup.setMetadataCompanyId(companyMetadata.getObjectId());
//        startup.setMetadataParsed(true);
//        startupRepository.save(startup);
//    }
}
