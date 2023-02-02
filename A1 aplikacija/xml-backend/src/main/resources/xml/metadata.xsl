<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:a1="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xs="http://www.w3.org/2001/XMLSchema#"
                xmlns:pred="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                xmlns:sh="https://schema.org/">

    <xsl:output method="xml" indent="yes"/>


    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="a1:zahtev">
        <zahtev vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                   about="http://www.ftn.uns.ac.rs/xwt/zahtev/{./a1:prijava/a1:broj_prijave}">
            <xsl:apply-templates select="node()|@*"/>
        </zahtev>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:prijava/a1:broj_prijave">
        <BrojPrijave property="pred:broj_prijave" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojPrijave>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:prijava/a1:datum_podnosenja">
        <DatumPodnosenja property="pred:datum_podnosenja" datatype="xs:date">
            <xsl:apply-templates select="node()|@*"/>
        </DatumPodnosenja>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:podnosilac/a1:lice/a1:kontakt/a1:email">
        <PodnosilacEmail property="pred:podnosilac_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PodnosilacEmail>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:autorsko_delo/a1:autori/a1:autor/a1:kontakt/a1:email">
        <AutorEmail property="pred:autor_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </AutorEmail>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:autorsko_delo/a1:naslov">
        <NaslovDela property="pred:naslov_dela" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </NaslovDela>
    </xsl:template>

    <xsl:template match="a1:zahtev/a1:broj_resenja">
        <BrojResenja property="pred:broj_resenja" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojResenja>
    </xsl:template>

</xsl:stylesheet>
