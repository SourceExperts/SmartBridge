package com.freemahn.smartbridge.dao.company;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CorporateAccount
{
    String location;
    String industry;
    String stage;
    LocalDate age;
}
