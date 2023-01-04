<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://www.ftn.uns.ac.rs/xwt"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="bookstore-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="bookstore-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Zahtev za priznanje patenta
                    </fo:block>
                    <fo:block text-indent="10px">
                        Total number of books in the bookstore:
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="p:zahtev/p:prijava/p:vrsta_prijave"/>
                        </fo:inline>
                    </fo:block>



                    <!-- ranije prijave -->
                    <fo:block text-indent="10px">
                        <fo:table font-family="serif" margin="50px auto 50px auto" border="1px">
                            <fo:table-column column-width="10%"/>
                            <fo:table-column column-width="40%"/>
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#a7aba8" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>#</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#a7aba8" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Datum podnosenja ranije prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#a7aba8" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Broj ranije prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#a7aba8" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>Dvoslovna oznaka drzave</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="p:zahtev/p:ranije_prijave/p:ranija_prijava">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="position()" />.
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="p:broj_prijave" />
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="p:datum_podnosenja"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell padding="10px">
                                            <fo:block>
                                                <xsl:value-of select="p:dvoslovna_oznaka"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
