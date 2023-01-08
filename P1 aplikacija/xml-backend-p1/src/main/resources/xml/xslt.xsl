<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:p="http://www.ftn.uns.ac.rs/xwt"
                version="2.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za priznanje patenta</title>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width: 100%;
                    }

                    body{
                    margin: 5%rem;
                    }

                    td, th {
                    border: 3px solid #dddddd;
                    text-align: left;
                    padding: 8px;
                    }

                    tr{
                    height: 3rem;
                    }

                    th{
                    background-color: #b6b6b6;
                    }

                    h2{
                    text-align: center;
                    }

                    .popunjava-zavod{
                    width: 50%;
                    }

                    .zavod-info{
                    margin-bottom: 10rem;
                    }

                    .ranije-prijave{
                    width: 80%;
                    margin: auto;
                    }

                    .podnosioc{
                    width: 80%;
                    margin: auto;
                    margin-bottom: 10rem;
                    }

                    .pronalazac{
                    width: 80%;
                    margin: auto;
                    margin-bottom: 10rem;
                    }

                    .opis{
                    font-size: 0.7rem;
                    }

                    .tdfill {
                    vertical-align: top;
                    text-align: left;
                    }

                    .ins{
                    font-size: 0.85rem;
                    color: gray;
                    }

                    .punomocnik{
                    width: 80%;
                    margin: auto;
                    margin-bottom: 5rem;
                    }

                </style>
            </head>
            <body>
                <div class="popunjava-zavod">
                    <table>
                        <tr>
                           <th colspan="2">Popunjava zavod</th>
                        </tr>

                        <tr>
                            <td colspan="2">
                                Broj prijave: <br/>
                                <b>P - <xsl:value-of  select="zahtev/prijava/broj_prijave"/></b>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Datum prijema: <br/>
                                <b><xsl:value-of  select="zahtev/prijava/datum_prijema"/></b>
                            </td>
                            <td>
                                Priznati datum podnošenja: <br/>
                                <b><xsl:value-of  select="zahtev/prijava/priznati_datum_podnosenja"/></b>
                            </td>
                        </tr>
                        <tr>
                            <td rowspan="2" colspan="2">
                                Pečat i potpis:
                            </td>
                        </tr>
                        <tr/>
                    </table>
                </div>

                <div class="zavod-info">
                    <p>
                        Republika Srbija <br/>
                        Zavod za intelektualnu svojinu <br/>
                        Kneginje Ljubice broj 5 <br/>
                        11000 Beograd <br/>
                    </p>
                </div>

                <h2>Zahtev za priznanje patenta</h2>

                <div class="podnosioc">
                    <table>
                        <tr>
                            <td colspan="3">
                                Polje broj I &#160;&#160;&#160;<b> Naziv pronalaska </b> <br/>
                                <p class="opis">
                                    * Naziv pronalaska treba da jasno i sažeto izražava suštinu pronalaska
                                        i ne sme da sadrži izmišljene ili komercijalne nazive, žigove, imena,
                                        šifre, uobičajeno skraćenice za proizvode i sl.
                                </p>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Na srpskom jeziku: <xsl:value-of  select="zahtev/pronalazak/naziv_pronalaska_srb"/>
                                <br/>
                                Na engleskom jeziku: <xsl:value-of  select="zahtev/pronalazak/naziv_pronalaska_eng"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Polje broj II &#160;&#160;&#160;<b> Podnosilac prijave </b> &#160;&#160;&#160;&#160;&#160;&#160; Podnosilac prijave je i pronalazac:
                                <input type="checkbox" disabled="true" checked="zahtev/podnosilac/@pronalazac"/>
                            </td>
                        </tr>

                        <tr>
                            <td rowspan="2" class="tdfill">
                                Ime i prezime/Poslovno ime:
                                <p class="opis">(prezime/poslovno ime upisati velikim slovima)</p>
                            </td>
                            <td rowspan="2" class="tdfill">Ulica i broj, poštanski broj, mesto i država:<br/><br/>
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/adresa/ulica"/></i> &#160;
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/adresa/broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/adresa/postanski_broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/adresa/mesto"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/adresa/drzava"/></i><br/>
                            </td>
                            <td class="tdfill">
                                Broj telefona: <br/>
                                <i class="ins"><xsl:value-of select="zahtev/podnosilac/lice/kontakt/telefon"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdfill"> Broj faksa: <br/>
                                <i class="ins"><xsl:value-of  select="zahtev/podnosilac/lice/kontakt/faks"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">Državljanstvo:
                                <i class="ins"><xsl:value-of  select="zahtev/podnosilac/drzavljanstvo"/></i> <br/>
                                <p class="opis">(popuniti samo za fizička lica)</p>
                            </td>
                            <td class="tdfill">E-pošta: <br/>
                                <i class="ins"><xsl:value-of  select="zahtev/podnosilac/lice/kontakt/email"/></i>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="pronalazac">
                    <table>
                        <tr>
                            <td colspan="5">
                                Polje broj III &#160;&#160;&#160; <b> Pronalazač </b> <br/> <br/>
                                Pronalazač želi da bude naveden u prijavi &#160;
                                <input type="checkbox" disabled="true" checked="zahtev/pronalazac/@naveden_u_prijavi"/>
                            </td>
                        </tr>
                        <tr>
                            <td rowspan="3" class="tdfill">
                                Ime i prezime/Poslovno ime:
                                <p class="opis">(prezime/poslovno ime upisati velikim slovima)</p>
                            </td>
                            <td rowspan="3" class="tdfill">Ulica i broj, poštanski broj, mesto i država:<br/><br/>
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/ulica"/></i> &#160;
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/postanski_broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/mesto"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/drzava"/></i><br/>
                            </td>
                            <td class="tdfill"> Broj telefona: <br/>
                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/kontakt/telefon"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdfill"> Broj faksa: <br/>
                                <i class="ins"><xsl:value-of  select="zahtev/pronalazac/lice/kontakt/faks"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdfill">E-pošta: <br/>
                                <i class="ins"><xsl:value-of  select="zahtev/pronalazac/lice/kontakt/email"/></i>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="punomocnik">
                    <table>
                        <tr>
                            <td colspan="3">
                                Polje broj IV  &#160;&#160;&#160; <b>Punomoćnik za zastupanje</b> <input type="checkbox" disabled="false'" checked="select='zahtev/punomoc/tip' == 'punomocnik_za_zastupanje'"/> <br/>
                                <b>Punomoćnik za prijem pismena</b> <input type="checkbox" checked="select='zahtev/punomoc/tip' != 'punomocnik_za_zastupanje'"/>
                                <br/>
                                <p class="opis">
                                    *Punomoćnik za zastupanje je lice koje po ovlašćenju podnosioca prijave preduzima radnje u upravnom
                                    postupku u granicama punomoćja <br/>
                                    *Punomoćnik za prijem pismena je lice koje podnosilac prijave odredio kao lice kome se upućuju
                                    sva pismena naslovljena na podnosioca
                                </p>
                            </td>
                        </tr>
                        <tr>
                            <td rowspan="2" class="tdfill">
                                Ime i prezime/Poslovno ime:
                                <p class="opis">(prezime/poslovno ime upisati velikim slovima)</p>
                            </td>
                            <td rowspan="2" class="tdfill">Ulica i broj, poštanski broj, mesto i država:<br/><br/>
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/ulica"/></i> &#160;
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/postanski_broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/mesto"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/drzava"/></i><br/>
                            </td>
                            <td class="tdfill">
                                Broj telefona: <br/>
                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/kontakt/telefon"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdfill">E-pošta: <br/>
                                <i class="ins"><xsl:value-of  select="zahtev/punomoc/lice/kontakt/email"/></i>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                                Polje broj V  &#160;&#160;&#160; <b>Adresa za dostavljanje</b>
                                <p class="opis">
                                    (ovo polje se popunjava ako podnosilac prijave, punomoćnik želi da se dostavljanje podnesaka
                                    vrši na drugoj adresi od njegove)
                                </p>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" class="tdfill">Ulica i broj, poštanski broj i mesto:<br/><br/>
                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/ulica"/></i> &#160;
                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/postanski_broj"/></i><br/>
                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/mesto"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Polje broj VI  &#160;&#160;&#160; <b>Način dostavljanja</b>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <p class="opis">
                                <input type="checkbox"/> &#160; Podnosilac prijave je saglasan da Zavod vrši dostavljanje pismena isključivo elektronskim
                                putem u formi elektronskog dokumenta(u ovom slučaju neophodna je registracija na portalu
                                "еУправе") <br/>
                                    <input type="checkbox"/> &#160; Podnosilac prijave je saglasan da Zavod vrši dostavljanje pismena u papirnoj formi
                                </p>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                                Polje broj VII  &#160;&#160;&#160; <b>Dopunska prijava</b> &#160; <input type="checkbox"/>
                                                &#160;&#160;&#160; <b>Izdvojena prijava</b> &#160; <input type="checkbox"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                                Broj prvobitne prijave / osnovne prijave, odnosno osnovnog patenta:
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                                Datum podnošenja prvobitne prijave / osnovne prijave:
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                                Polje broj VIII  &#160;&#160;&#160; <b>Zahtev za priznanje prava prvenstva iz ranijih prijava:</b> &#160;
                            </td>
                        </tr>

                    </table>
                </div>


                <div class="ranije-prijave">
                    <table>
                        <tr>
                            <th>R.br.</th>
                            <th>Datum podnošenja ranije prijave</th>
                            <th>Broj ranije prijave</th>
                            <th>Dvoslovna oznaka države</th>
                        </tr>
                        <xsl:for-each select="zahtev/ranije_prijave/ranija_prijava">
                            <tr>
                                <td>
                                    <xsl:value-of  select="position()"/>
                                </td>
                                <td>
                                    <xsl:value-of select="broj_prijave" />
                                </td>
                                <td>
                                    <xsl:value-of select="datum_podnosenja" />
                                </td>
                                <td>
                                    <xsl:value-of select="dvoslovna_oznaka" />
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>