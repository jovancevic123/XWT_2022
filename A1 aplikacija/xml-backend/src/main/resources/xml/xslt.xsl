<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.ftn.uns.ac.rs/xwt A-1.xsd"
                version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>ZAHTEV ZA UNOŠENJE U EVIDENCIJU I DEPONOVANJE AUTORSKIH DELA</title>
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
                        margin-bottom: 0;
                        padding-bottom:0;
                    }

                    .zahtev-header{
                        text-align:center;
                        margin-top:50px;
                        margin-bottom:50px;
                    }

                    .main-div{
                        border:1px solid black;
                        padding: 30px;
                    }
                    .kontakt-table{
                        border-collapse: collapse;
                        width:100%;
                    }
                    .paragraph{
                        margin-top: 40px;
                        margin-bottom:20px;
                    }
                    .content{
                        margin-bottom:20px;
                        margin-left:20px;
                        color:gray;
                    }
                    .naslov-dela{
                        color:gray;
                        margin-left:20px;
                    }
                    .flex-div{
                        display:flex;
                    }
                    .potpis{
                        width:50%;
                        float:right;
                    }

                    .hr{
                        widht:100%;
                        background-color:black;
                        margin-bottom:0;
                        padding-bottom:0;
                    }

                    .h4{
                        text-align:right;
                        margin-top:0;
                        margin-bottom:0;
                        padding-bottom:0;
                    }

                    .potpis p{
                        margin-top:0;
                        padding-top:0;
                    }

                    .popunjava-zavod{
                        text-align:center;
                    }

                    .prijava-table{
                        margin-top:400px;
                        border-collapse: collapse;
                        width:40%;
                        float:right;
                        font-size:24px;
                    }
                    .prijava-table tr{
                        height:200px;
                        padding:20px;
                    }

                    .prijava-table h2{
                        font-size:35px;
                        font-weight:800;
                        margin-left:10px;
                        text-align:left;
                    }
                    .kraj{
                        font-size:16px;
                        margin-top:2px;
                    }

                    .gray{
                        color:gray;
                    }


                </style>
            </head>
            <body>

                <div class="zavod-info">
                    <p class="zavod-text"><xsl:value-of  select="zahtev/zavod/naziv"/> &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
                        &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
                        OBRAZAC A-1</p>
                    <xsl:value-of  select="zahtev/zavod/adresa/mesto"/>,&#160;<xsl:value-of  select="zahtev/zavod/adresa/ulica"/>&#160; <xsl:value-of  select="zahtev/zavod/adresa/broj"/>

                    <h2 class="zahtev-header">ZAHTEV ZA UNOŠENJE U EVIDENCIJU I DEPONOVANJE AUTORSKIH DELA</h2>
                </div>

                <div class="main-div">
                    <p>
                        1)Podnosilac - ime, prezime, adresa i državljanstvo autora ili drugog nosioca autorskog prava ako je podnosilac fizičko lice, odnosno poslovno ime i sedište nosioca autorskog prava ako je podnosilac pravno lice*:
                    </p>
                    <br/>
                    <br/>
                    <p class="content">
                        <xsl:choose>
                            <xsl:when test="zahtev/podnosilac/lice[@type='TFizickoLice']">
                                <xsl:value-of  select="zahtev/podnosilac/lice/ime"/>,
                                <xsl:value-of  select="zahtev/podnosilac/lice/prezime"/>,<br/>
                                <xsl:value-of  select="zahtev/podnosilac/lice/adresa/ulica"/>&#160;
                                <xsl:value-of  select="zahtev/podnosilac/lice/adresa/broj"/>,
                                <xsl:value-of  select="zahtev/podnosilac/lice/adresa/mesto"/>,
                                <xsl:value-of  select="zahtev/podnosilac/lice/adresa/drzava"/>,<br/>
                                <xsl:value-of  select="zahtev/podnosilac/lice/drzavljanstvo"/>
                            </xsl:when>
                            <xsl:when test="zahtev/podnosilac/lice[@type='TPravnoLice']">
                                <xsl:value-of  select="zahtev/podnosilac/lice/poslovno_ime"/>,
                                <xsl:value-of  select="zahtev/podnosilac/lice/sediste"/>,
                            </xsl:when>
                        </xsl:choose>
                        <table class="kontakt-table" border="1">
                            <tr>
                                <td>telefon:&#160; <xsl:value-of  select="zahtev/podnosilac/lice/kontakt/telefon"/></td>
                                <td>email:&#160;<xsl:value-of  select="zahtev/podnosilac/lice/kontakt/email"/></td>
                            </tr>
                        </table>
                    </p>

                    <p class="paragraph">2)Pseudonim ili znak autora, (ako ga ima):</p><br/>
                    <p class="content">
                        <xsl:for-each select="zahtev/autorsko_delo/autori">
                            <xsl:value-of  select="autor/pseudonim"/>,
                        </xsl:for-each>
                    </p>
                    <p class="paragraph">3)Ime, prezime i adresa punomoćnika, ako se prijava podnosi preko punomoćnika:</p><br/>
                    <p class="content">
                        <xsl:if test="zahtev/punomocnik">
                            <xsl:value-of  select="zahtev/punomocnik/ime"/>,
                            <xsl:value-of  select="zahtev/punomocnik/prezime"/>,<br/>
                            <xsl:value-of  select="zahtev/punomocnik/lice/adresa/ulica"/>&#160;
                            <xsl:value-of  select="zahtev/punomocnik/lice/adresa/broj"/>,
                            <xsl:value-of  select="zahtev/punomocnik/lice/adresa/mesto"/>,
                            <xsl:value-of  select="zahtev/punomocnik/lice/adresa/drzava"/><br/>
                        </xsl:if>
                    </p>
                    <p class="paragraph">4) Naslov autorskog dela, odnosno alternativni naslov, ako ga ima, po kome autorsko delo može da se identifikuje*:</p><br/>
                    <p class="content">
                        <xsl:value-of  select="zahtev/autorsko_delo/naslov"/>
                    </p>
                    <p class="paragraph">5) Podaci o naslovu autorskog dela na kome se zasniva delo prerade, ako je u pitanju autorsko delo prerade, kao i podatak o autoru izvornog dela:</p><br/>
                    <div class="flex-div">
                        <xsl:if test="zahtev/autorsko_delo/delo_prerade">
                            <p class="naslov-dela">
                                Zasniva se na naslov dela: <xsl:value-of  select="zahtev/autorsko_delo/delo_prerade/naslov_dela_prerade"/><br/>
                                Autori:
                            </p>
                            <xsl:for-each select="zahtev/autorsko_delo/delo_prerade/autori">
                                <p class="content">
                                    <xsl:value-of  select="autor/ime"/>,
                                    <xsl:value-of  select="autor/prezime"/>,<br/>
                                    <xsl:value-of  select="autor/adresa/ulica"/>&#160;
                                    <xsl:value-of  select="autor/adresa/broj"/>,
                                    <xsl:value-of  select="autor/adresa/mesto"/>,
                                    <xsl:value-of  select="autor/adresa/drzava"/>,<br/>
                                    <xsl:value-of  select="autor/drzavljanstvo"/>,<br/>
                                    <xsl:value-of  select="autor/kontakt/telefon"/>,&#160;
                                    <xsl:value-of  select="autor/kontakt/email"/><br/><br/>
                                </p>
                            </xsl:for-each>
                        </xsl:if>
                    </div>

                    <p class="paragraph">6) Podaci o vrsti autorskog dela (književno delo, muzičko delo, likovno delo, računarski program i dr.) *:</p><br/>
                    <p class="content">
                        <xsl:value-of  select="zahtev/autorsko_delo/vrsta_dela"/>
                    </p>

                    <p class="paragraph">7) Podaci o formi zapisa autorskog dela (štampani tekst, optički disk i slično) *:</p><br/>
                    <p class="content">
                        <xsl:value-of  select="zahtev/autorsko_delo/forma_zapisa_dela"/>
                    </p>

                    <p class="paragraph">8) Podaci o autoru ako podnosilac prijave iz tačke 1. ovog zahteva nije autor i
                        to: prezime, ime, adresa i državljanstvo autora (grupe autora ili koautora), a ako su u pitanju
                        jedan ili više autora koji nisu živi, imena autora i godine smrti autora a ako je u pitanju
                        autorsko delo anonimnog autora navod da je autorsko delo delo anonimnog autora:</p><br/>
                    <p class="content">
                        <xsl:if test="zahtev/podnosilac[@podnosilac_je_autor='true']">
                            Podnosilac je autor
                        </xsl:if>
                        <xsl:if test="zahtev/autorsko_delo/anoniman_autor='true'">
                            Autorsko delo anonimnog autora
                        </xsl:if>
                        <xsl:if test="zahtev/podnosilac[@podnosilac_je_autor='false']">
                            <xsl:for-each select="zahtev/autorsko_delo/autori">
                                <p class="content">
                                    <xsl:value-of  select="autor/prezime"/>,
                                    <xsl:value-of  select="autor/ime"/>,<br/>
                                    <xsl:choose>
                                        <xsl:when test="autor/godina_smrti">
                                            <xsl:value-of  select="autor/godina_smrti"/>,
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of  select="autor/adresa/ulica"/>&#160;
                                            <xsl:value-of  select="autor/adresa/broj"/>,
                                            <xsl:value-of  select="autor/adresa/mesto"/>,
                                            <xsl:value-of  select="autor/adresa/drzava"/>,<br/>
                                            <xsl:value-of  select="autor/drzavljanstvo"/>,<br/>
                                            <xsl:value-of  select="autor/kontakt/telefon"/>,&#160;
                                            <xsl:value-of  select="autor/kontakt/email"/><br/><br/>
                                        </xsl:otherwise>
                                    </xsl:choose>

                                </p>
                            </xsl:for-each>
                        </xsl:if>

                    </p>

                    <p class="paragraph">9) Podatak da li je u pitanju autorsko delo stvoreno u radnom odnosu:</p><br/>
                    <p class="content">
                        <xsl:choose>
                            <xsl:when test="zahtev/autorsko_delo/stvoreno_u_radnom_odnosu='true'">
                                Da
                            </xsl:when>
                            <xsl:otherwise>
                                Ne
                            </xsl:otherwise>
                        </xsl:choose>
                    </p>

                    <p class="paragraph">10) Način korišćenja autorskog dela ili nameravani način korišćenja autorskog dela:</p><br/>
                    <p class="content">
                        <xsl:value-of  select="zahtev/autorsko_delo/nacin_koriscenja"/>
                    </p>

                    <div class="flex-div" style="margin-bottom:180px;">
                        <p class="paragraph">11)</p><br/>
                        <div class="potpis">
                            <hr class="hr"/>
                            <h4 class="h4">Podnosilac prijave, nosilac prava</h4>
                            <p>(mesto za potpis fizičkog lica, odnosno potpis zastupnika pravnog lica ili ovlašćenog predstavnika u pravnom licu)*</p>
                        </div>
                    </div>

                    <p class="paragraph">12) Prilozi koji se podnose uz zahtev:</p><br/>
                    <h2 class="popunjava-zavod">POPUNJAVA ZAVOD</h2>
                    <h4>Prilozi uz prijavu:</h4>
                    <p>opis autorskog dela (ako je delo podneto na optičkom disku)</p>
                    <p>primer autorskog dela (slika, video zapis, audio zapis)</p>

                    <table class="prijava-table" border="1">
                        <tr>
                            <td>
                                <p>Broj pijave</p>
                                <h2>A-<xsl:value-of  select="zahtev/prijava/broj_prijave"/></h2>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>Datum podnošenja:</p>
                                <h2><xsl:value-of  select="zahtev/prijava/datum_podnosenja"/></h2>
                            </td>
                        </tr>
                    </table>
                    <div style="height:680px"/>
                </div>
                    <p class="kraj">Rubrike u zahtevu A-1 koje su označene sa * moraju da budu obavezno popunjene.</p>
            </body>

        </html>
    </xsl:template>
</xsl:stylesheet>