package com.freemahn.smartbridge.controller;

import com.freemahn.smartbridge.dao.Startup;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoaderController
{

    @GetMapping("/api/data/startups/load")
    public void fetchAllStartupsData()
    {

    }
}
