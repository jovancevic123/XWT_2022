import { Injectable } from '@angular/core';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  public zahtevSpecification = {
    elements: {
      zahtev: {
        menu:[
          {
            caption: 'Add <prijava>',
            action: Xonomy.newElementChild,
            actionParameter: '<prijava></prijava>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("prijava")
            },
          },
          {
            caption: 'Add <pronalazak>',
            action: Xonomy.newElementChild,
            actionParameter: '<pronalazak></pronalazak>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pronalazak")
            },
          },
          {
            caption: 'Add <podnosilac>',
            action: Xonomy.newElementChild,
            actionParameter: '<podnosilac></podnosilac>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("podnosilac")
            },
          },
          {
            caption: 'Add <pronalazac>',
            action: Xonomy.newElementChild,
            actionParameter: '<pronalazac></pronalazac>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pronalazac")
            },
          },
          {
            caption: 'Add <punomoc>',
            action: Xonomy.newElementChild,
            actionParameter: '<punomoc></punomoc>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("punomoc")
            },
          },
          {
            caption: 'Add <adresa_dostave>',
            action: Xonomy.newElementChild,
            actionParameter: '<adresa_dostave></adresa_dostave>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("adresa_dostave")
            },
          },
          {
            caption: 'Add <nacin_dostavljanja>',
            action: Xonomy.newElementChild,
            actionParameter: '<nacin_dostavljanja></nacin_dostavljanja>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("nacin_dostavljanja")
            },
          },
          {
            caption: 'Add <ranije_prijave>',
            action: Xonomy.newElementChild,
            actionParameter: '<ranije_prijave></ranije_prijave>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("ranije_prijave")
            },
          }],
        attributes:{

        }
      },
      nacin_dostavljanja:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askPicklist,
        askerParameter: ["elektronska_forma", "papirna_forma"]
      },

      //PRIJAVA
      prijava:{
        mustBeBefore: ['pronalazak', 'adresa_dostave', 'nacin_dostavljanja'],
        menu: [{
                caption: 'Add <vrsta_prijave>',
                action: Xonomy.newElementChild,
                actionParameter: '<vrsta_prijave></vrsta_prijave>',
                hideIf: function(jsElement: any){
                  return jsElement.hasChildElement("vrsta_prijave")
                },
              },
          ],
      },

      vrsta_prijave:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askPicklist,
        askerParameter: ["izdvojena", "dopunska"]
      },

      // PRONALAZAK
      pronalazak:{
          mustBeBefore: ['adresa_dostave', 'nacin_dostavljanja', 'podnosilac'],
          menu: [{
                  caption: 'Add <naziv_pronalaska_srb>',
                  action: Xonomy.newElementChild,
                  actionParameter: '<naziv_pronalaska_srb></naziv_pronalaska_srb>',
                  hideIf: function(jsElement: any){
                    return jsElement.hasChildElement("naziv_pronalaska_srb")
                  },
                },
                {
                  caption: 'Add <naziv_pronalaska_eng>',
                  action: Xonomy.newElementChild,
                  actionParameter: '<naziv_pronalaska_eng></naziv_pronalaska_eng>',
                  hideIf: function(jsElement: any){
                    return jsElement.hasChildElement("naziv_pronalaska_eng")
                  },
                },
            ],
      },

      // PODNOSILAC
      podnosilac:{
          menu: [{
            caption: 'Add <fizicko_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<fizicko_lice></fizicko_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add <pravno_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<pravno_lice></pravno_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add @pronalazac',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'pronalazac', value: 'false'},
            hideIf: function(jsElement: any){
              return jsElement.hasAttribute("pronalazac")
            }
          },
          {
            caption: 'Add <drzavljanstvo>',
            action: Xonomy.newElementChild,
            actionParameter: '<drzavljanstvo></drzavljanstvo>',
          },
        ],
        attributes:{
          "pronalazac":{
            asker: Xonomy.askPicklist,
            askerParameter: [
              {value: "true", caption: "jeste"},
              {value: "false", caption: "nije"},
            ]
          }
        }
      },

        // PRONALAZAC
        pronalazac:{
          menu: [{
            caption: 'Add <fizicko_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<fizicko_lice></fizicko_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add <pravno_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<pravno_lice></pravno_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add @naveden_u_prijavi',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'naveden_u_prijavi', value: 'false'},
            hideIf: function(jsElement: any){
              return jsElement.hasAttribute("naveden_u_prijavi")
            }
          },
        ],
        attributes:{
          "naveden_u_prijavi":{
            asker: Xonomy.askPicklist,
            askerParameter: [
              {value: "true", caption: "jeste"},
              {value: "false", caption: "nije"},
            ]
          }
        }
      },

        // PUNOMOC
        punomoc:{
          menu: [{
            caption: 'Add <fizicko_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<fizicko_lice></fizicko_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add <pravno_lice>',
            action: Xonomy.newElementChild,
            actionParameter: '<pravno_lice></pravno_lice>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("pravno_lice") || jsElement.hasChildElement("fizicko_lice")
            },
          },
          {
            caption: 'Add <tip>',
            action: Xonomy.newElementChild,
            actionParameter: '<tip></tip>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("tip")
            }
          },
        ],
      },


      //ADRESA_DOSTAVE
      adresa_dostave:{
        menu: [
          {
            caption: 'Add <ulica>',
            action: Xonomy.newElementChild,
            actionParameter: '<ulica></ulica>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("ulica")
            }
          },
          {
            caption: 'Add <broj>',
            action: Xonomy.newElementChild,
            actionParameter: '<broj></broj>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("broj")
            }
          },
          {
            caption: 'Add <postanski_broj>',
            action: Xonomy.newElementChild,
            actionParameter: '<postanski_broj></postanski_broj>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("postanski_broj")
            }
          },
          {
            caption: 'Add <mesto>',
            action: Xonomy.newElementChild,
            actionParameter: '<mesto></mesto>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("mesto")
            }
          },
          {
            caption: 'Add <drzava>',
            action: Xonomy.newElementChild,
            actionParameter: '<drzava></drzava>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("drzava")
            }
          },
        ],
      },

      // ADRESA
      adresa:{
        menu: [
          {
            caption: 'Add <ulica>',
            action: Xonomy.newElementChild,
            actionParameter: '<ulica></ulica>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("tip")
            }
          },
          {
            caption: 'Add <broj>',
            action: Xonomy.newElementChild,
            actionParameter: '<broj></broj>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("broj")
            }
          },
          {
            caption: 'Add <postanski_broj>',
            action: Xonomy.newElementChild,
            actionParameter: '<postanski_broj></postanski_broj>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("postanski_broj")
            }
          },
          {
            caption: 'Add <mesto>',
            action: Xonomy.newElementChild,
            actionParameter: '<mesto></mesto>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("mesto")
            }
          },
          {
            caption: 'Add <drzava>',
            action: Xonomy.newElementChild,
            actionParameter: '<drzava></drzava>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("drzava")
            }
          },
        ],
      },

      // KONTAKT
      kontakt:{
        menu: [
          {
            caption: 'Add <email>',
            action: Xonomy.newElementChild,
            actionParameter: '<email></email>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("email")
            }
          },
          {
            caption: 'Add <telefon>',
            action: Xonomy.newElementChild,
            actionParameter: '<telefon></telefon>',
            hasText: true,
            asker: Xonomy.askString,
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("telefon")
            }
          },
          {
            caption: 'Add <fax>',
            action: Xonomy.newElementChild,
            actionParameter: '<fax></fax>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("fax")
            }
          }
        ],
      },

      ime:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      prezime:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      poslovno_ime:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      fax:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      telefon:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      email:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      ulica:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      broj:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      postanski_broj:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      mesto:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      drzava:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      naziv_pronalaska_srb:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      naziv_pronalaska_eng:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      drzavljanstvo: {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
      },

      tip:{
        hasText: true,
        oneliner: true,
        asker: Xonomy.askPicklist,
          askerParameter: [
            {value: "punomocnik_za_zastupanje", caption: "punomocnik za zastupanje"},
            {value: "punomocnik_za_prijem_pismena", caption: "punomocnik za prijem pismena"},
          ]
      },

      // FIZICKO_LICE  
      fizicko_lice:{
        menu: [{
          caption: 'Add <adresa>',
          action: Xonomy.newElementChild,
          actionParameter: '<adresa></adresa>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("adresa")
          },
        },
        {
          caption: 'Add <kontakt>',
          action: Xonomy.newElementChild,
          actionParameter: '<kontakt></kontakt>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("kontakt")
          },
        },
        {
          caption: 'Add <ime>',
          action: Xonomy.newElementChild,
          actionParameter: '<ime></ime>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("ime")
          },
        },
        {
          caption: 'Add <prezime>',
          action: Xonomy.newElementChild,
          actionParameter: '<prezime></prezime>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("prezime")
          },
        },
      ]},

      // PRAVNO_LICE  
      pravno_lice:{
        menu: [{
          caption: 'Add <adresa>',
          action: Xonomy.newElementChild,
          actionParameter: '<adresa></adresa>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("adresa")
          },
        },
        {
          caption: 'Add <kontakt>',
          action: Xonomy.newElementChild,
          actionParameter: '<kontakt></kontakt>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("kontakt")
          },
        },
        {
          caption: 'Add <poslovno_ime>',
          action: Xonomy.newElementChild,
          actionParameter: '<poslovno_ime></poslovno_ime>',
          hideIf: function(jsElement: any){
            return jsElement.hasChildElement("poslovno_ime")
          },
        },
      ]},


      // RANIJE PRIJAVE
      ranije_prijave:{
        menu: [{
            caption: 'Add <ranija_prijava>',
            action: Xonomy.newElementChild,
            actionParameter: '<ranija_prijava></ranija_prijava>',
        }],
      },
      ranija_prijava:{
          menu: [{
            caption: 'Add <broj_prijave>',
            action: Xonomy.newElementChild,
            actionParameter: '<broj_prijave></broj_prijave>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("broj_prijave")
            },
          },
          {
            caption: 'Add <datum_podnosenja>',
            action: Xonomy.newElementChild,
            actionParameter: '<datum_podnosenja></datum_podnosenja>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("datum_podnosenja")
            },
          },
          {
            caption: 'Add <dvoslovna_oznaka>',
            action: Xonomy.newElementChild,
            actionParameter: '<dvoslovna_oznaka></dvoslovna_oznaka>',
            hideIf: function(jsElement: any){
              return jsElement.hasChildElement("dvoslovna_oznaka")
            },
          }],
      },
      broj_prijave:{
        hasText: true,
        oneliner: true,
      },
      datum_podnosenja:{
        hasText: true,
        oneliner: true,
      },
      dvoslovna_oznaka:{
        hasText: true,
        oneliner: true,
      },
    }
  }
}
