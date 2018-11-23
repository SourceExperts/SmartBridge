package com.freemahn.smartbridge.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class StartupList
{
    private List<StartupDTO> items;


    public StartupList()
    {
        items = new ArrayList<>();
    }
}
