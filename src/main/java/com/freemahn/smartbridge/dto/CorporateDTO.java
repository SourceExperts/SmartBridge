package com.freemahn.smartbridge.dto;

import com.freemahn.smartbridge.dao.Logo;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CorporateDTO
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
    private boolean publishChallenges;

    public CorporateDTO()
    {
        industries = new ArrayList<>();
    }



}
