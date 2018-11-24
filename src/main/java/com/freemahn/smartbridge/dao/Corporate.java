package com.freemahn.smartbridge.dao;

import com.freemahn.smartbridge.dao.company.CompanyPreferableOptions;
import com.freemahn.smartbridge.dto.CorporateDTO;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "corporate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)

public class Corporate
{
    @ToString.Include
    @Id
    private Long id;

    private String type;
    @ToString.Include
    private String name;
    private String shortDescription;
    @Lob
    private String description;
    private String website;
    private String city;
    private String country;
    private boolean published;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> industries;
    @Fetch(FetchMode.SELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> interests;
    private boolean publishChallenges;
    @Embedded
    private Logo logo;

    @Embedded
    private CompanyPreferableOptions account;


    public Corporate(CorporateDTO corporateDTO)
    {
        this.id = Long.parseLong(corporateDTO.getId());
        this.type = corporateDTO.getType();
        this.name = corporateDTO.getName();
        this.shortDescription = corporateDTO.getShortDescription();
        this.description = corporateDTO.getDescription();
        this.website = corporateDTO.getWebsite();
        this.city = corporateDTO.getCity();
        this.country = corporateDTO.getCountry();
        this.published = corporateDTO.isPublished();
        this.industries = corporateDTO.getIndustries();
        this.publishChallenges = corporateDTO.isPublishChallenges();
    }
}
