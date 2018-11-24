package com.freemahn.smartbridge.dao;

import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class CompanyMetadata
{
    private Long objectId;
    private String objectType;
    private String objectName;
    private String objectSlug;
    private String companyDomain;
    private Long companyFunding;
    private String companyKeywords;
    private Long companyMattermarkScore;
    private Long investorFunding;


    public CompanyMetadata(CompanyMetadataDTO dto)
    {
        this.objectId = dto.getObject_id();
        this.objectType = dto.getObject_type();
        this.objectName = dto.getObject_name();
        this.objectSlug = dto.getObject_slug();
        this.companyDomain = dto.getCompany_domain();
        this.companyFunding = dto.getCompany_funding();
        this.companyKeywords = dto.getCompany_keywords();
        this.companyMattermarkScore = dto.getCompany_mattermark_score();
        this.investorFunding = dto.getInvestor_funding();
    }
}
