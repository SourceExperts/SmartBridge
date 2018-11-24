package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.repository.StartupRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartupController
{
    private final StartupRepository startupRepository;


    public StartupController(StartupRepository startupRepository)
    {
        this.startupRepository = startupRepository;
    }


    @GetMapping("/api/startups/all")
    public List<Startup> getAll()
    {
        return startupRepository.findAll();
    }


    @GetMapping("api/startups/suggested")
    public List<Startup> getSuggested(@RequestParam long companyId)
    {
        return startupRepository.findAllByMetadataCompanyIdNotNull();
    }
}
