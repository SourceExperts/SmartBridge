package com.freemahn.smartbridge.dao;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Logo
{
    @Id
    private Long id;
    private String imageServiceUrl;
    private String fileName;
}
