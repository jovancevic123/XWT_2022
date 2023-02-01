<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xwt="http://www.ftn.uns.ac.rs/xwt"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.ftn.uns.ac.rs/xwt ZH-1.xsd">
    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za priznanje ziga</title>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width: 100%;
                    }

                    body{
                    margin: 5rem;
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

                    h1{
                    text-align: center;
                    }

                    .popunjava-zavod{
                    width: 50%;
                    }

                    .subtitle{
                    margin-bottom: 10rem;
                    text-align: center;
                    }

                    .ranije-prijave{
                    width: 80%;
                    margin: auto;
                    }

                    .podnosilac{
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

                    .popuniti_na_racunaru{
                    text-align:center;
                    }

                    .circled {
                    border-radius: 50%;
                    border: 2px solid red;
                    padding: 10px;
                    }

                    .pecat{
                    text-allign:center;
                    }

                </style>
            </head>
            <body>

                <h1>Zahtev za priznanje ziga</h1>
                <p class="subtitle"><b>Zavodu za intelektualnu svojinu
                    Kneginje Ljubice broj 5
                    11000 Beograd </b>
                </p>
                <p class="popuniti_na_racunaru">(popuniti na racunaru)</p>

                <table>
                    <tr>
                        <td colspan="3"><b>1. Podnosilac prijave: </b>ime i prezime/poslovno ime, ulica i broj, postanski broj, mesto, drzava prebivalista/sedista</td>
                    </tr>
                    <tr rowspan="2">
                        <td colspan="3" style="height: 40px">
                            <xsl:choose>
                                <xsl:when test="zahtev/podnosilac/@type='TFizickoLice'">
                                    <b><xsl:value-of  select="zahtev/podnosilac/ime"/>, </b>
                                    <b><xsl:value-of  select="zahtev/podnosilac/prezime"/>, </b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <b><xsl:value-of  select="zahtev/podnosilac/poslovno_ime"/>, </b>
                                </xsl:otherwise>
                            </xsl:choose>
                            <b><xsl:value-of  select="zahtev/podnosilac/adresa/ulica"/>, </b>
                            <b><xsl:value-of  select="zahtev/podnosilac/adresa/broj"/>, </b>
                            <b><xsl:value-of  select="zahtev/podnosilac/adresa/postanski_broj"/>, </b>
                            <b><xsl:value-of  select="zahtev/podnosilac/adresa/mesto"/>, </b>
                            <b><xsl:value-of  select="zahtev/podnosilac/adresa/drzava"/></b>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1">telefon: <b><xsl:value-of  select="zahtev/podnosilac/kontakt/telefon"/></b></td>
                        <td colspan="1">e-mail: <b><xsl:value-of  select="zahtev/podnosilac/kontakt/email"/></b></td>
                        <td colspan="1">faks: <b><xsl:value-of  select="zahtev/podnosilac/kontakt/fax"/></b></td>
                    </tr>

                    <tr>
                        <td colspan="3"><b>2. Punomocnik </b>ime i prezime/poslovno ime, ulica i broj, postanski broj, mesto, drzava prebivalista/sedista</td>
                    </tr>
                    <tr>
                        <td colspan="3" style="height: 40px">
                            <xsl:choose>
                                <xsl:when test="zahtev/punomocnik/@type='TFizickoLice'">
                                    <b><xsl:value-of  select="zahtev/punomocnik/ime"/>, </b>
                                    <b><xsl:value-of  select="zahtev/punomocnik/prezime"/>, </b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <b><xsl:value-of  select="zahtev/punomocnik/poslovno_ime"/>, </b>
                                </xsl:otherwise>
                            </xsl:choose>
                            <b><xsl:value-of  select="zahtev/punomocnik/adresa/ulica"/>, </b>
                            <b><xsl:value-of  select="zahtev/punomocnik/adresa/broj"/>, </b>
                            <b><xsl:value-of  select="zahtev/punomocnik/adresa/postanski_broj"/>, </b>
                            <b><xsl:value-of  select="zahtev/punomocnik/adresa/mesto"/>, </b>
                            <b><xsl:value-of  select="zahtev/punomocnik/adresa/drzava"/></b>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1">telefon: <b><xsl:value-of  select="zahtev/punomocnik/kontakt/telefon"/></b></td>
                        <td colspan="1">e-mail: <b><xsl:value-of  select="zahtev/punomocnik/kontakt/email"/></b></td>
                        <td colspan="1">faks: <b><xsl:value-of  select="zahtev/punomocnik/kontakt/fax"/></b></td>
                    </tr>

                    <tr>
                        <td colspan="3"><b>3. Podaci o zajednickom predstavniku ako postoji vise podnosilaca prijave </b>ime i prezime/poslovno ime, ulica i broj, postanski broj, mesto, drzava prebivalista/sedista</td>
                    </tr>
                    <tr>
                        <td colspan="3" style="height: 40px">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1">telefon:</td>
                        <td colspan="1">e-mail:</td>
                        <td colspan="1">faks:</td>
                    </tr>
                </table>
                <table>
                    <tr colspan="4">
                        <td colspan="3"><b>4. Prijava se podnosi za (upisati X):</b></td>
                        <td colspan="1"><b>c) izgled znaka:</b></td>
                    </tr>
                    <tr colspan="4">
                        <td rowspan="3" style="width: 10px"><b>a)</b></td>
                        <td colspan="1">individualni zig</td>
                        <td colspan="1">
                        <xsl:variable name="zigType" select="zahtev/zig/tip_ziga"/>
                        <xsl:if test="$zigType = 'individualni_zig'">
                            <b>X</b>
                        </xsl:if>
                        </td>
                        <td colspan="1" rowspan="12"></td>
                    </tr>
                    <tr colspan="4">
                        <td colspan="1">kolektivni zig</td>
                        <td colspan="1">
                            <xsl:variable name="zigType" select="zahtev/zig/tip_ziga"/>
                            <xsl:if test="$zigType = 'kolektivni_zig'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>
                    <tr colspan="4">
                        <td colspan="1">zig garancije</td>
                        <td colspan="1">
                            <xsl:variable name="zigType" select="zahtev/zig/tip_ziga"/>
                            <xsl:if test="$zigType = 'zig_garancije'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="4">
                        <td rowspan="5" style="width: 10px"><b>b)</b></td>
                        <td colspan="1">verbalni znak (znak u reci)</td>
                        <td colspan="1">
                            <xsl:variable name="znakType" select="zahtev/zig/znak/tip_znaka"/>
                            <xsl:if test="$znakType = 'verbalni_znak'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="4">
                        <td colspan="1">graficki znak; boju, kombinaciju boja</td>
                        <td colspan="1">
                            <xsl:variable name="znakType" select="zahtev/zig/znak/tip_znaka"/>
                            <xsl:if test="$znakType = 'graficki_znak'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="4">
                        <td colspan="1">kombinovani znak</td>
                        <td colspan="1">
                            <xsl:variable name="znakType" select="zahtev/zig/znak/tip_znaka"/>
                            <xsl:if test="$znakType = 'kombinovani_znak'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="4">
                        <td colspan="1">trodimenzionalni znak</td>
                        <td colspan="1">
                            <xsl:variable name="znakType" select="zahtev/zig/znak/tip_znaka"/>
                            <xsl:if test="$znakType = 'trodimenzionalni_znak'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="4">
                        <td colspan="1">drugu vrstu znaka (navesti koju)</td>
                        <td colspan="1">
                            <xsl:variable name="znakType" select="zahtev/zig/znak/tip_znaka"/>
                            <xsl:if test="$znakType = 'druga_vrsta_znaka'">
                                <b>X</b>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr colspan="3">
                        <td colspan="3">
                            <b>5. Naznacenje boje, odnosno boja iz kojih se znak sastoji: </b>
                            <xsl:value-of select="zahtev/zig/znak/boje"/>
                        </td>
                    </tr>

                    <tr colspan="3">
                        <td colspan="3">
                            <b>6. Transliteracija znaka*: </b>
                        </td>
                    </tr>

                    <tr colspan="3">
                        <td colspan="3">
                            <b>7. Prevod znaka*: </b>
                        </td>
                    </tr>

                    <tr colspan="3">
                        <td colspan="3">
                            <b>8. Opis znaka: </b>
                            <xsl:value-of select="zahtev/zig/znak/opis_znaka"/>
                        </td>
                    </tr>

                    <tr colspan="23">
                        <td colspan="23">
                            <b>9. Zaokruziti brojeve klasa ribe u usluga prema Nicanskoj klasifikaciji: </b>
                        </td>
                    </tr>
                </table>

                <table>
                    <tr colspan="23">
                        <xsl:call-template name="genNumbers">
                            <xsl:with-param name="start" select="1"/>
                            <xsl:with-param name="end" select="24"/>
                            <xsl:with-param name="counter" select="1"/>
                        </xsl:call-template>
                    </tr>
                </table>
                <table>
                    <tr colspan="23">
                        <xsl:call-template name="genNumbers">
                            <xsl:with-param name="start" select="24"/>
                            <xsl:with-param name="end" select="46"/>
                            <xsl:with-param name="counter" select="1"/>
                        </xsl:call-template>
                    </tr>
                </table>
                <p>*Popuniti samo ako je znak ili element znaka ispisan slovima koja nisu cirilicna ili latinicna</p>

                <table>
                    <tr colspan="3">
                        <td><b>11. Placene takse:</b></td>
                        <td><b>Dinara</b></td>
                        <td colspan="1" rowspan="4" class="pecat">
                            <b>Potpis podnosioca zahteva</b>
                            <br></br>
                            <i>* Pecat, ukoliko je potreban u skladu sa zakonom</i>
                        </td>
                    </tr>
                    <tr>
                        <td><b>a) osnovna taksa</b></td>
                        <xsl:for-each select="zahtev/placene_takse/taksa">
                            <xsl:choose>
                                <xsl:when test="tip_takse='osnovna_taksa'">
                                    <td>
                                        <xsl:value-of select="iznos"></xsl:value-of>
                                    </td>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </tr>
                    <tr style="width: 10px">
                        <td>
                            <b>b) za _____ klasa</b>
                            <br></br>
                            <b>c) za graficko resenje</b>
                        </td>
                        <xsl:for-each select="zahtev/placene_takse/taksa">
                            <xsl:choose>
                                <xsl:when test="tip_takse='za_graficko_resenje'">
                                    <td>
                                        <xsl:value-of select="iznos"></xsl:value-of>
                                    </td>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <td>UKUPNO</td>
                        <td>
                            <xsl:variable name="total" select="sum(zahtev/placene_takse/taksa/iznos)"/>
                            <xsl:value-of select="$total"/>
                        </td>
                    </tr>
                </table>

                <br></br>
                <br></br>

                <table>
                    <tr>
                        <td colspan="3" style="text-align:center">POPUNJAVA ZAVOD</td>
                    </tr>
                    <tr>
                        <td colspan="2"><b>Prilozi uz zahtev:</b></td>
                        <td colspan="1" rowspan="9" style="text-align:center">
                            Broj prijave ziga:
                            <br></br>
                            <b>Z - <xsl:value-of select="zahtev/zig/broj_prijave_ziga"></xsl:value-of></b>
                            <br></br>
                            <br></br>
                            <b>Datum podnosenja:</b>
                            <br></br>
                            <b><u><xsl:value-of select="zahtev/zig/datum_podnosenja"></xsl:value-of></u></b>
                        </td>
                    </tr>
                    <tr>
                        <td>Primerak znaka</td>
                        <td>
                            <xsl:value-of select="zahtev/prilozi/primerak_znaka"></xsl:value-of>
                        </td>
                    </tr>
                    <tr>
                        <td>Spisak robe i usluga **</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Punomocje</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Generalno punomocje ranije prilozeno</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Punomocje ce biti naknadno dostavljeno</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Opsti akt o kolektivnom zigu/zigu garancije</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Dokaz o pravu prvenstva</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Dokaz o uplati takse</td>
                        <td></td>
                    </tr>
                </table>
                <br></br>
                <br></br>
                <p>** Uz zaokruzivanje broja klase robe/usluga Nicanske klasifikacije
                u rubrici 9 dostavlja se i spisak koji sadrzi konkretne nazive robe
                koju podnosilac priajve proizvodi, odnosno usluga koje pruza.
                 U cilju odredjenja obima zastite koja se stice zigom, spisak treba da
                sadrzi jasne i precizne nazive robe i usluga. U tu svrhu mogu se koristiti
                pojmovi iz detaljne Liste roba i usluga ili MGS Manager aplikacije,
                 dostupnih na sajtu Zavoda. Ukoliko se u spisak unose termini iz Liste klasa
                Nicanske klasifikacije, zastita obuhvata samo tako imenovane,
                konkretne robe/usluge u njihovom jasnom i nedvosmislenom znacenju.</p>

<!--                <div class="pronalazac">-->
<!--                    <table>-->
<!--                        <tr>-->
<!--                            <td colspan="5">-->
<!--                                Polje broj III &#160;&#160;&#160; <b> Pronalazač </b> <br/> <br/>-->
<!--                                Pronalazač želi da bude naveden u prijavi &#160;-->
<!--                                <input type="checkbox" disabled="true" checked="zahtev/pronalazac/@naveden_u_prijavi"/>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td rowspan="3" class="tdfill">-->
<!--                                Ime i prezime/Poslovno ime:-->
<!--                                <p class="opis">(prezime/poslovno ime upisati velikim slovima)</p>-->
<!--                            </td>-->
<!--                            <td rowspan="3" class="tdfill">Ulica i broj, poštanski broj, mesto i država:<br/><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/ulica"/></i> &#160;-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/postanski_broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/mesto"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/adresa/drzava"/></i><br/>-->
<!--                            </td>-->
<!--                            <td class="tdfill"> Broj telefona: <br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/pronalazac/lice/kontakt/telefon"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td class="tdfill"> Broj faksa: <br/>-->
<!--                                <i class="ins"><xsl:value-of  select="zahtev/pronalazac/lice/kontakt/faks"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td class="tdfill">E-pošta: <br/>-->
<!--                                <i class="ins"><xsl:value-of  select="zahtev/pronalazac/lice/kontakt/email"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                    </table>-->
<!--                </div>-->

<!--                <div class="punomocnik">-->
<!--                    <table>-->
<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Polje broj IV  &#160;&#160;&#160; <b>Punomoćnik za zastupanje</b> <input type="checkbox" disabled="false'" checked="select='zahtev/punomoc/tip' == 'punomocnik_za_zastupanje'"/> <br/>-->
<!--                                <b>Punomoćnik za prijem pismena</b> <input type="checkbox" checked="select='zahtev/punomoc/tip' != 'punomocnik_za_zastupanje'"/>-->
<!--                                <br/>-->
<!--                                <p class="opis">-->
<!--                                    *Punomoćnik za zastupanje je lice koje po ovlašćenju podnosioca prijave preduzima radnje u upravnom-->
<!--                                    postupku u granicama punomoćja <br/>-->
<!--                                    *Punomoćnik za prijem pismena je lice koje podnosilac prijave odredio kao lice kome se upućuju-->
<!--                                    sva pismena naslovljena na podnosioca-->
<!--                                </p>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td rowspan="2" class="tdfill">-->
<!--                                Ime i prezime/Poslovno ime:-->
<!--                                <p class="opis">(prezime/poslovno ime upisati velikim slovima)</p>-->
<!--                            </td>-->
<!--                            <td rowspan="2" class="tdfill">Ulica i broj, poštanski broj, mesto i država:<br/><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/ulica"/></i> &#160;-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/postanski_broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/mesto"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/adresa/drzava"/></i><br/>-->
<!--                            </td>-->
<!--                            <td class="tdfill">-->
<!--                                Broj telefona: <br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/punomoc/lice/kontakt/telefon"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td class="tdfill">E-pošta: <br/>-->
<!--                                <i class="ins"><xsl:value-of  select="zahtev/punomoc/lice/kontakt/email"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Polje broj V  &#160;&#160;&#160; <b>Adresa za dostavljanje</b>-->
<!--                                <p class="opis">-->
<!--                                    (ovo polje se popunjava ako podnosilac prijave, punomoćnik želi da se dostavljanje podnesaka-->
<!--                                    vrši na drugoj adresi od njegove)-->
<!--                                </p>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td colspan="3" class="tdfill">Ulica i broj, poštanski broj i mesto:<br/><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/ulica"/></i> &#160;-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/postanski_broj"/></i><br/>-->
<!--                                <i class="ins"><xsl:value-of select="zahtev/adresa_dostave/mesto"/></i>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Polje broj VI  &#160;&#160;&#160; <b>Način dostavljanja</b>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                <p class="opis">-->
<!--                                    <input type="checkbox"/> &#160; Podnosilac prijave je saglasan da Zavod vrši dostavljanje pismena isključivo elektronskim-->
<!--                                    putem u formi elektronskog dokumenta(u ovom slučaju neophodna je registracija na portalu-->
<!--                                    "еУправе") <br/>-->
<!--                                    <input type="checkbox"/> &#160; Podnosilac prijave je saglasan da Zavod vrši dostavljanje pismena u papirnoj formi-->
<!--                                </p>-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Polje broj VII  &#160;&#160;&#160; <b>Dopunska prijava</b> &#160; <input type="checkbox"/>-->
<!--                                &#160;&#160;&#160; <b>Izdvojena prijava</b> &#160; <input type="checkbox"/>-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Broj prvobitne prijave / osnovne prijave, odnosno osnovnog patenta:-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Datum podnošenja prvobitne prijave / osnovne prijave:-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                Polje broj VIII  &#160;&#160;&#160; <b>Zahtev za priznanje prava prvenstva iz ranijih prijava:</b> &#160;-->
<!--                            </td>-->
<!--                        </tr>-->

<!--                    </table>-->
<!--                </div>-->


<!--                <div class="ranije-prijave">-->
<!--                    <table>-->
<!--                        <tr>-->
<!--                            <th>R.br.</th>-->
<!--                            <th>Datum podnošenja ranije prijave</th>-->
<!--                            <th>Broj ranije prijave</th>-->
<!--                            <th>Dvoslovna oznaka države</th>-->
<!--                        </tr>-->
<!--                        <xsl:for-each select="zahtev/ranije_prijave/ranija_prijava">-->
<!--                            <tr>-->
<!--                                <td>-->
<!--                                    <xsl:value-of  select="position()"/>-->
<!--                                </td>-->
<!--                                <td>-->
<!--                                    <xsl:value-of select="broj_prijave" />-->
<!--                                </td>-->
<!--                                <td>-->
<!--                                    <xsl:value-of select="datum_podnosenja" />-->
<!--                                </td>-->
<!--                                <td>-->
<!--                                    <xsl:value-of select="dvoslovna_oznaka" />-->
<!--                                </td>-->
<!--                            </tr>-->
<!--                        </xsl:for-each>-->
<!--                    </table>-->
<!--                </div>-->
            </body>
        </html>
    </xsl:template>
    <xsl:template name="genNumbers">
        <xsl:param name="start"/>
        <xsl:param name="end"/>
        <xsl:param name="counter"/>
        <xsl:if test="$counter &lt; $end">
            <xsl:variable name="currentNumber" select="$start + $counter - 1"/>
            <xsl:for-each select="zahtev/zig/brojevi_klasa_robe">
                <xsl:variable name="brojKlasaRobe" select="broj"></xsl:variable>
                <xsl:choose>
                    <xsl:when test="$currentNumber = $brojKlasaRobe">
                        <td class="circled" colspan="1">
                            <xsl:value-of select="$currentNumber"/>
                        </td>
                    </xsl:when>
                    <xsl:otherwise>
                        <td colspan="1">
                            <xsl:value-of select="$currentNumber"/>
                        </td>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
            <xsl:call-template name="genNumbers">
                <xsl:with-param name="start" select="$start"/>
                <xsl:with-param name="end" select="$end"/>
                <xsl:with-param name="counter" select="$counter+1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>