package com.freemahn.smartbridge.dao.company;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CompanyPreferableOptions
{
    /*
    * Location of startup*/
    String location;
    /**
     * Industry of startup
     */
    String industry;
    /**
     * Current stage of startup
     */
    String stage;
    /**
     * How much time has passed since startup launch
     */
    LocalDate age;
}
