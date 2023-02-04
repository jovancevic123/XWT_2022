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

    <xsl:template match="zh1:zahtev">
        <zahtev vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                about="http://www.ftn.uns.ac.rs/xwt/zahtev/{./zh1:zig/zh1:broj_prijave_ziga}">
            <xsl:apply-templates select="node()|@*"/>
        </zahtev>
    </xsl:template>

    <xsl:template match="zh1:zahtev/zh1:zig/zh1:broj_prijave_ziga">
        <BrojPrijaveZiga property="pred:broj_prijave_ziga" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojPrijaveZiga>
    </xsl:template>

    <xsl:template match="zh1:zahtev/zh1:zig/zh1:datum_podnosenja">
        <DatumPodnosenja property="pred:datum_podnosenja" datatype="xs:date">
            <xsl:apply-templates select="node()|@*"/>
        </DatumPodnosenja>
    </xsl:template>

    <xsl:template match="zh1:zahtev/zh1:podnosilac/zh1:kontakt/zh1:email">
        <PodnosilacEmail property="pred:podnosilac_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PodnosilacEmail>
    </xsl:template>

    <xsl:template match="zh1:zahtev/zh1:punomocnik/zh1:kontakt/zh1:email">
        <PunomocnikEmail property="pred:punomocnik_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PunomocnikEmail>
    </xsl:template>

    <xsl:template match="zh1:zahtev/zh1:broj_resenja">
        <BrojResenja property="pred:broj_resenja" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojResenja>
    </xsl:template>

</xsl:stylesheet>
