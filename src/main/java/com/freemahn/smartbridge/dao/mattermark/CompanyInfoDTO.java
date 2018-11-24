package com.freemahn.smartbridge.dao.mattermark;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Embeddable
@Data
public class CompanyInfoDTO
{
//    Long id;

    @JsonProperty("name")
    String mattermarkName;
    @JsonProperty("mattermark_score")
    Integer mattermarkScore;
    Integer employees;
    String stage;
    @JsonProperty("total_funding")
    Long totalFunding;
    String location;

//    String city;
    String zip;
//    String country;

    @JsonProperty("revenue_range")
    String revenueRange;
    @JsonProperty("est_founding_date")
    LocalDate estFoundingDate;

    @JsonProperty("business_models")
    @Fetch(FetchMode.SELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> businessModels;

    @Fetch(FetchMode.SELECT)
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> industries;

}
