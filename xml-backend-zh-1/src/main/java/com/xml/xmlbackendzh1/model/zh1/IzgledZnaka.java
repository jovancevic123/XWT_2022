package com.xml.xmlbackendzh1.model.zh1;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "src"
})
public class IzgledZnaka {
    @XmlAttribute(required=true, name = "src")
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
