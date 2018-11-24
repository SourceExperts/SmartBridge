package com.freemahn.smartbridge.dao;

import lombok.Data;

@Data
public class CompanyMetadataDTO
{
    private String object_type;
    private String object_name;
    private String object_slug;
    private String company_domain;
    private Long company_funding;
    private String company_keywords;
    private Long company_mattermark_score;
    private Long investor_funding;
    private Long object_id;
}
