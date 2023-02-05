<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:zh1="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xs="http://www.w3.org/2001/XMLSchema#"
                xmlns:pred="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                xmlns:sh="https://schema.org/">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="zh1:resenje">
        <resenje vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                 about="http://www.ftn.uns.ac.rs/xwt/resenje/{./zh1:broj_resenja}">
            <xsl:apply-templates select="node()|@*"/>
        </resenje>
    </xsl:template>

    <xsl:template match="zh1:resenje/zh1:broj_resenja">
        <BrojResenja property="pred:broj_resenja" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojResenja>
    </xsl:template>

    <xsl:template match="zh1:resenje/zh1:datum_odgovora">
        <DatumOdgovora property="pred:datum_odgovora" datatype="xs:date">
            <xsl:apply-templates select="node()|@*"/>
        </DatumOdgovora>
    </xsl:template>

    <xsl:template match="zh1:resenje/zh1:prihvacena">
        <Prihvacena property="pred:prihvacena" datatype="xs:boolean">
            <xsl:apply-templates select="node()|@*"/>
        </Prihvacena>
    </xsl:template>
</xsl:stylesheet>