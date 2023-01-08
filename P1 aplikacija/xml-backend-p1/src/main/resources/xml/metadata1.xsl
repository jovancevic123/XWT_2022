<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p1="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xs="http://www.w3.org/2001/XMLSchema#"
                xmlns:pred="http://www.ftn.uns.ac.rs/rdf/examples/predicate/">

    <xsl:output method="xml" indent="yes"/>


    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="p1:zahtev">
        <zahtev vocab="http://www.ftn.uns.ac.rs/rdf/examples/predicate/"
                   about="http://www.ftn.uns.ac.rs/xwt/zahtev">
            <xsl:apply-templates select="node()|@*"/>
        </zahtev>
    </xsl:template>

    <xsl:template match="p1:zahtev/p1:prijava/p1:broj_prijave">
        <BrojPrijave property="pred:broj_prijave" datatype="xs:string" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </BrojPrijave>
    </xsl:template>

</xsl:stylesheet>
