package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.service.StartupSuggestionService;
import java.util.List;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorporateController
{
    private final CorporateRepository corporateRepository;
    private final StartupSuggestionService startupSuggestionService;


    public CorporateController(CorporateRepository corporateRepository, StartupSuggestionService startupSuggestionService)
    {
        this.corporateRepository = corporateRepository;
        this.startupSuggestionService = startupSuggestionService;
    }


    @GetMapping("/api/corporates/all")
    public List<Corporate> getAll()
    {
        return corporateRepository.findAll();
    }


    @GetMapping("api/corporates/{id}/account")
    public CompanyPreferableOptions getAccount(@PathVariable("id") Long id)
    {

        return corporateRepository.findById(id).get().getAccount();
    }


    @PostMapping("api/corporates/{id}/account")
    public void updateAccount(@PathVariable("id") Long id, @RequestBody CompanyPreferableOptions account)
    {
        Corporate corporate = corporateRepository.findById(id).get();
        corporate.setAccount(account);
        corporateRepository.save(corporate);
    }



    @GetMapping("/api/corporates/{id}/match")
    public void performMatch(@PathVariable("id") long corporateId)
    {
        startupSuggestionService.fetchBridges(corporateId);
    }
    @PostMapping("/api/corporates/{id}/match")
    public void performMatch(@PathVariable("id") long corporateId, @RequestBody Payload payload)
    {
        startupSuggestionService.createBridge(corporateId, payload);
    }


    @Data
    public class Payload
    {
        long startupId;
        String name;
        String description;
    }
}
