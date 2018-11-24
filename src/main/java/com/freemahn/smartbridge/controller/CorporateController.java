package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.dao.company.CorporateAccount;
import com.freemahn.smartbridge.repository.CorporateRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorporateController
{
    private final CorporateRepository corporateRepository;


    public CorporateController(CorporateRepository corporateRepository)
    {
        this.corporateRepository = corporateRepository;
    }


    @GetMapping("/api/corporates/all")
    public List<Corporate> getAll()
    {
        return corporateRepository.findAll();
    }


    @GetMapping("api/corporates/{id}/account")
    public CorporateAccount getAccount(@PathVariable("id") Long id)
    {

        return corporateRepository.findById(id).get().getAccount();
    }


    @PostMapping("api/corporates/{id}/account")
    public void updateAccount(@PathVariable("id") Long id, @RequestBody CorporateAccount account)
    {
        Corporate corporate = corporateRepository.findById(id).get();
        corporate.setAccount(account);
        corporateRepository.save(corporate);
    }
}
