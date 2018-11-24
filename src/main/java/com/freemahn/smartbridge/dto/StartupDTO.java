package com.freemahn.smartbridge.dto;

import com.freemahn.smartbridge.dao.Logo;
import com.freemahn.smartbridge.dao.Startup;
import com.freemahn.smartbridge.dao.mattermark.CompanyInfoDTO;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartupDTO
{
    private String type;
    private String id;
    private String name;
    private String shortDescription;
    private String description;
    private String founded;
    private String website;
    private String city;
    private String country;
    private boolean published;
    private Logo logo;
    private ArrayList<String> industries;

    public StartupDTO()
    {
        industries = new ArrayList<>();
    }


//    public StartupDTO(Startup startup)
//    {
//        this.id = startup.getId().toString();
//        this.type = startup.getType();
//        this.name = startup.getName();
//        this.shortDescription = startup
//
//    }
}
