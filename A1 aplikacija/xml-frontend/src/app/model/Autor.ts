import { Adresa } from './Adresa';
import { Kontakt } from './Kontakt';
export interface Autor{
    kontakt:Kontakt,
    ime:string,
    prezime:string,
    adresa:Adresa
    drzavljanstvo:string
}
