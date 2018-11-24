package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.repository.StartupRepository;
import com.freemahn.smartbridge.service.StartupSuggestionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartupController
{
    private final StartupRepository startupRepository;
    private final CorporateRepository corporateRepository;
    private final StartupSuggestionService startupSuggestionService;


    public StartupController(
        StartupRepository startupRepository,
        CorporateRepository corporateRepository,
        StartupSuggestionService startupSuggestionService)
    {
        this.startupRepository = startupRepository;
        this.corporateRepository = corporateRepository;
        this.startupSuggestionService = startupSuggestionService;
    }


    @GetMapping("/api/startups/all")
    public List<Startup> getAll()
    {
        return startupRepository.findAll();
    }


    @GetMapping("/api/startups/suggested")
    public List<Startup> getSuggested(@RequestParam long companyId)
    {
        return startupSuggestionService.doSuggestionMagic(companyId);
    }


}
