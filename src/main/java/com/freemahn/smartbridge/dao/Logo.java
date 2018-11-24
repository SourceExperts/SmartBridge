package com.freemahn.smartbridge.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Logo
{
    @JsonProperty("id")
    private String logoId;
    @JsonProperty("imageServiceUrl")
    private String logoImageServiceUrl;
    @JsonProperty("fileName")
    private String logoFileName;
}
