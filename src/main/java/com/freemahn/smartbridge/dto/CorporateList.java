package com.freemahn.smartbridge.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CorporateList
{
    private final List<CorporateDTO> items = new ArrayList<>();

}
