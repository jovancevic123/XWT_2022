package com.xml.xmlbackendzh1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedSearchDto {
    private String meta;
    private String value;
    private String operator;
}
