package com.xml.xmlbackendzh1.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriloziDto {
    private String primerakZnaka;
    private String spisakRobeIUsluga;
    private String punomocje;
    private String generalnoPunomocje;
    private String punomocjeNaknadnoDostavljeno;
    private String opstiAkt;
    private String dokazOPravuPrvenstva;
    private String dokazOUplatiTakse;
}
