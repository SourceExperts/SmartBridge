package com.freemahn.smartbridge.dao;

import com.freemahn.smartbridge.dao.mattermark.CompanyInfoDTO;
import com.freemahn.smartbridge.dto.StartupDTO;
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
@Table(name = "startup")
@Data
@NoArgsConstructor
@AllArgsConstructor

@ToString(onlyExplicitlyIncluded = true)
public class Startup
{
    @Id
    @ToString.Include
    private Long id;
    @ToString.Include
    private String name;

    private String type;

    private String shortDescription;
    @Lob
    private String description;
    private String founded;
    private String website;
    private String city;
    private String country;
    private boolean published;

    @Fetch(FetchMode.SELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> industries;
    @Embedded
    private Logo logo;


    @Embedded
    private CompanyInfoDTO info;

    private Long metadataCompanyId;

    private boolean metadataParsed = false;


    public Startup(StartupDTO startupDTO)
    {
        this.id = Long.parseLong(startupDTO.getId());
        this.type = startupDTO.getType();
        this.name = startupDTO.getName();
        this.shortDescription = startupDTO.getShortDescription();
        this.description = startupDTO.getDescription();
        this.founded = startupDTO.getFounded();
        this.website = startupDTO.getWebsite();
        this.city = startupDTO.getCity();
        this.country = startupDTO.getCountry();
        this.published = startupDTO.isPublished();
        this.industries = startupDTO.getIndustries();
        this.logo = startupDTO.getLogo();

    }
}
