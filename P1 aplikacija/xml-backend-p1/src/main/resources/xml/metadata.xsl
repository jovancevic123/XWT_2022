<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:p1="http://www.ftn.uns.ac.rs/xwt/"
>

    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="Prijava">
                <xsl:variable name="broj_prijave">
                    <xsl:value-of select="zahtev/prijava/broj_prijave"/>
                </xsl:variable>
                <xsl:variable name="naziv_pronalaska_srb">
                    <xsl:value-of select="zahtev/pronalazak/naziv_pronalaska_srb"/>
                </xsl:variable>
                <p1:broj_prijave>
                    <xsl:value-of select="$broj_prijave"/>
                </p1:broj_prijave>
                <p1:naziv_pronalaska_srb>
                    <xsl:value-of select="$naziv_pronalaska_srb"/>
                </p1:naziv_pronalaska_srb>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>