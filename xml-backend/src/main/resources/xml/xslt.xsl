<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.ftn.uns.ac.rs/xwt A-1.xsd"
                xmlns="http://www.ftn.uns.ac.rs/xwt"
                version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za unošenje u evidenciju i delovanje autorskih dela</title>
                <style>

                    body{
                    margin: 5%rem;
                    font-family: arial, sans-serif;
                    }

                    .zavod-info{
                        border:1px solid black;
                        padding: 10px;

                    }
                    .zavod-text{
                        font-weight:800;
                        text-transform: uppercase;
                        font-size:16px;
                    }

                    .zahtev-header{
                        text-align:center;
                        margin-top:50px;
                        margin-bottom:50px;
                    }

                    .main-div{
                        border:1px solid black;
                    }
                    .kontakt-table{
                        width:100%;
                    }

                </style>
            </head>
            <body>

                <div class="zavod-info">
                    <p class="zavod-text"><xsl:value-of  select="zahtev/zavod/naziv"/> &#160;&#160;&#160;&#160;OBRAZAC A-1</p>
                    <p><xsl:value-of  select="zahtev/zavod/adresa/mesto"/>,&#160;<xsl:value-of  select="zahtev/zavod/adresa/ulica"/>&#160; <xsl:value-of  select="zahtev/zavod/adresa/broj"/></p>

                    <h2 class="zahtev-header">ZAHTEV ZA UNOŠENJE U EVIDENCIJU I DELOVANJE AUTRSKIH DELA</h2>
                </div>

                <div class="main-div">
                    1)Podnosilac - ime, prezime, adresa i državljanstvo autora ili drugog nosioca autorskog prava ako je podnosilac fizičko lice, odnosno poslovno ime i sedište nosioca autorskog prava ako je podnosilac pravno lice*:
                    <br/>
                    <br/>
                    <xsl:choose>
                        <xsl:when test="@xsi:type='TFizickoLice'">
                            <xsl:value-of  select="ime"/>,
                            <xsl:value-of  select="prezime"/>,<br/>
                            <xsl:value-of  select="ulica"/>&#160;
                            <xsl:value-of  select="broj"/>,
                            <xsl:value-of  select="mesto"/>,
                            <xsl:value-of  select="drzava"/>,<br/>
                            <xsl:value-of  select="drzavljanstvo"/>
                        </xsl:when>
                        <xsl:when test="@xsi:type='TPravnoLice'">
                            <xsl:value-of  select="poslovno_ime"/>,
                            <xsl:value-of  select="sediste"/>,
                        </xsl:when>
                    </xsl:choose>
                    <table class=".kontakt-table" border="1">
                        <tr>
                            <td>telefon:&#160; <xsl:value-of  select="zahtev/podnosilac/lice/kontakt/telefon"/></td>
                            <td>email:&#160;<xsl:value-of  select="zahtev/podnosilac/lice/kontakt/email"/></td>
                        </tr>
                    </table>
                </div>
            </body>

        </html>
    </xsl:template>
</xsl:stylesheet>