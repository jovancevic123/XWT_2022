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

    <xsl:template match="p1:zahtev">
        <zahtev vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                   about="http://www.ftn.uns.ac.rs/xwt/zahtev/{./p1:prijava/p1:broj_prijave}">
            <xsl:apply-templates select="node()|@*"/>
        </zahtev>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:prijava/p1:broj_prijave">
        <BrojPrijave property="pred:broj_prijave" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojPrijave>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:pronalazak/p1:naziv_pronalaska_srb">
        <NazivSRB property="pred:naziv_srb" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </NazivSRB>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:pronalazac/p1:lice/p1:kontakt/p1:email">
        <PronalazacEmail property="pred:pronalazac_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PronalazacEmail>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:podnosilac/p1:lice/p1:kontakt/p1:email">
        <PodnosilacEmail property="pred:podnosilac_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PodnosilacEmail>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:punomoc/p1:lice/p1:kontakt/p1:email">
        <PunomocEmail property="pred:punomoc_email" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </PunomocEmail>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:pronalazak/p1:naziv_pronalaska_eng">
        <NazivENG property="pred:naziv_eng" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </NazivENG>
    </xsl:template>

    <xsl:template match="//p1:prijava/p1:datum_prijema">
        <DatumPrijema property="pred:datum_prijema" datatype="xs:date">
            <xsl:apply-templates select="node()|@*"/>
        </DatumPrijema>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:broj_resenja">
        <BrojResenja property="pred:broj_resenja" datatype="xs:string">
            <xsl:apply-templates select="node()|@*"/>
        </BrojResenja>
    </xsl:template>

</xsl:stylesheet>
