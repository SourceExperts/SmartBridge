package com.freemahn.smartbridge.dao;

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

@Entity
@Table(name = "startup")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Startup
{
    @Id
    //    @GeneratedValue(generator = "startup_generator")
    //    @SequenceGenerator(
    //        name = "startup_generator",
    //        sequenceName = "startup_sequence",
    //        initialValue = 1000
    //    )
    private Long id;

    private String type;
    private String name;
    private String shortDescription;
    @Lob
    private String description;
    private String founded;
    private String website;
    private String city;
    private String country;
    private boolean published;
    @ElementCollection(fetch= FetchType.EAGER)
    private List<String> industries;
    @Embedded
    private Logo logo;

    @Embedded
    private CompanyMetadata companyMetadata;


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

    }
}
