package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naslovDelaPrerade", "autori"
})
public class DeloPrerade {

    @XmlElement(name="naslov_dela_prerade", required=true)
    private String naslovDelaPrerade;

    @XmlElementWrapper(name="autori", required=false)
    @XmlElement(name="autor", required=true)
    private List<Autor> autori = new ArrayList<Autor>();

}
