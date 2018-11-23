package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Corporate;
import com.freemahn.smartbridge.repository.CorporateRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorporateController
{
    private final CorporateRepository corporateRepository;


    public CorporateController(CorporateRepository corporateRepository)
    {
        this.corporateRepository = corporateRepository;
    }


    @GetMapping("/api/corporate/all")
    public List<Corporate> getAll()
    {
        return corporateRepository.findAll();
    }
}
