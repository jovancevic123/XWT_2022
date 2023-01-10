package com.xml.backend.p1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingRequestDto {
    private String brojPrijave;
    private String nazivPodnosioca;
    private String nazivPatenta;
}
