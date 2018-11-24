package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dao.match.Bridge;
import com.freemahn.smartbridge.dto.Payload;
import com.freemahn.smartbridge.repository.CorporateRepository;
import com.freemahn.smartbridge.service.BridgeService;
import com.freemahn.smartbridge.service.StartupSuggestionService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorporateController
{
    private final CorporateRepository corporateRepository;
    private final StartupSuggestionService startupSuggestionService;
    private final BridgeService bridgeService;


    public CorporateController(
        CorporateRepository corporateRepository,
        StartupSuggestionService startupSuggestionService,
        BridgeService bridgeService)
    {
        this.corporateRepository = corporateRepository;
        this.startupSuggestionService = startupSuggestionService;
        this.bridgeService = bridgeService;
    }


    @GetMapping("/api/corporates/all")
    public List<Corporate> getAll()
    {
        return corporateRepository.findAll();
    }


    @GetMapping("api/corporates/{id}/account")
    public CompanyPreferableOptions getPrefferableOptions(@PathVariable("id") Long id)
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
    public List<Bridge> performMatch(@PathVariable("id") long corporateId)
    {
       return bridgeService.fetchBridges(corporateId);
    }
    
    @PostMapping("/api/corporates/{id}/match")
    public void performMatch(@PathVariable("id") long corporateId, @RequestBody Payload payload)
    {
        bridgeService.createBridge(corporateId, payload);
    }


    @GetMapping("/api/corporates/{id}/explore")
    public List<Startup> getExplorationStartups(@PathVariable("id") long companyId)
    {
        return startupSuggestionService.doExploreMagic(companyId);
    }


}
