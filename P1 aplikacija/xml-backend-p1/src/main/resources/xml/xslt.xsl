<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://www.ftn.uns.ac.rs/xwt"
                version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>P1</title>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width:
                    100%;
                    }
                    td, th {
                    border: 1px solid #dddddd;
                    text-align: left;
                    padding: 8px;
                    }
                    tr:nth-child(even) {
                    background-color: #dddddd;
                    }
                </style>
            </head>
            <body>
                <h2>Zahtev P1</h2>
                <table>
                    <tr>
                        <th>R.br.</th>
                        <th>Datum podnosenja ranije prijave</th>
                        <th>Broj ranije prijave</th>
                        <th>Dvoslovna oznaka drzave</th>
                    </tr>
<!--                    <xsl:for-each select="book">-->
<!--                        <tr>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="book/@id" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="author" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="title" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="genre" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="price" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="publish_date" />-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <xsl:value-of select="description" />-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                    </xsl:for-each>-->
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>