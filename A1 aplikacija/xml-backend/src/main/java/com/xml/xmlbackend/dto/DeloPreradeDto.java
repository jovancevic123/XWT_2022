package com.xml.xmlbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeloPreradeDto {
    private String naslovDela;
    private List<AutorDto> autoriDelaPrerade;

}
