<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p1="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xs="http://www.w3.org/2001/XMLSchema#"
                xmlns:pred="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                xmlns:sh="https://schema.org/">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="p1:resenje">
        <resenje vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                about="http://www.ftn.uns.ac.rs/xwt/resenje">
            <xsl:apply-templates select="node()|@*"/>
        </resenje>
    </xsl:template>

    <xsl:template match="p1:resenje/p1:broj_resenja">
        <BrojResenja property="pred:broj_resenja" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojResenja>
    </xsl:template>

    <xsl:template match="p1:resenje/p1:datum_odgovora">
        <DatumOdgovora property="pred:datum_odgovora" datatype="xs:date">
            <xsl:apply-templates select="node()|@*"/>
        </DatumOdgovora>
    </xsl:template>

</xsl:stylesheet>