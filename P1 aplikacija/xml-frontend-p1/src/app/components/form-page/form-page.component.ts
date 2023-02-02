import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { RanijaPrijava } from 'src/app/model/RanijaPrijava';
import { Adresa } from 'src/app/model/Adresa';
import { Kontakt } from 'src/app/model/Kontakt';
import { PatentService } from 'src/app/services/patent.service';

@Component({
  selector: 'app-form-page',
  templateUrl: './form-page.component.html',
  styleUrls: ['./form-page.component.css']
})
export class FormPageComponent implements OnInit{

    form: FormGroup;

    //adrese
    podnosilacAdresa: Adresa;
    pronalazacAdresa: Adresa;
    punomocAdresa: Adresa;
    adresaDostave: Adresa;

    //kontakti
    podnosilacKontakt: Kontakt;
    pronalazacKontakt: Kontakt;
    punomocKontakt: Kontakt;

    fizikoLicePodnosilacChecked: boolean = true;
    fizikoLicePronalazacChecked: boolean = true;
    fizikoLicePunomocChecked: boolean = true;
    elektronskaDostavaChecked: boolean = true;
    tipPunomocnikaZaZastupanjeChecked: boolean = true;
    dopunskaPrijavaChecked: boolean = true;
    podnosilacJePronalazacChecked: boolean = false;
    ranijePrijave: RanijaPrijava[] = [];

    constructor(private patentService: PatentService){}

    ngOnInit(){
      this.form = new FormGroup({
        nazivSRB: new FormControl('',[Validators.required]),
        nazivENG: new FormControl('',[Validators.required]),

        //podnosilac
        imePodnosilac: new FormControl('',[Validators.required]),
        prezimePodnosilac: new FormControl('',[Validators.required]),
        drzavljanstvoPodnosilac: new FormControl('',[Validators.required]),
        poslovnoImePodnosilac: new FormControl('',[Validators.required]),
        tipLicaPodnosilac: new FormControl('1',[Validators.required]),
        podnosilacJePronalazac: new FormControl(false),

        //pronalazac
        imePronalazac: new FormControl('',[Validators.required]),
        prezimePronalazac: new FormControl('',[Validators.required]),
        drzavljanstvoPronalazac: new FormControl('',[Validators.required]),
        poslovnoImePronalazac: new FormControl('',[Validators.required]),
        tipLicaPronalazac: new FormControl('1',[Validators.required]),
        navedenUPrijavi: new FormControl(false),

        //punomoc
        imePunomoc: new FormControl('',[Validators.required]),
        prezimePunomoc: new FormControl('',[Validators.required]),
        drzavljanstvoPunomoc: new FormControl('',[Validators.required]),
        poslovnoImePunomoc: new FormControl('',[Validators.required]),
        tipLicaPunomoc: new FormControl('1',[Validators.required]),
        drugaAdresaDostave: new FormControl(false),
        tipPunomocnika: new FormControl('1'),

        //ostalo
        elektronskaDostava: new FormControl('1'),
        vrstaPrijave: new FormControl('1')
      });
    }

    get tipLicaPodnosilac(){
      return this.form.get("tipLicaPodnosilac");
    }

    get tipLicaPronalazac(){
      return this.form.get("tipLicaPronalazac");
    }

    get tipLicaPunomoc(){
      return this.form.get("tipLicaPunomoc");
    }

    get nazivSRB(){
      return this.form.get("nazivSRB");
    }

    get nazivENG(){
      return this.form.get("nazivENG");
    }
    
    onSubmit(){
      let body = this.form.getRawValue();

      if(this.podnosilacJePronalazacChecked){
        body = {...body,
          "imePronalazac": body.imePodnosilac,
          "prezimePronalazac": body.prezimePodnosilac,
          "drzavljanstvoPronalazac": body.drzavljanstvoPodnosilac,
          "poslovnoImePronalazac": body.poslovnoImePodnosilac,
          "tipLicaPronalazac": body.tipLicaPodnosilac,
          "navedenUPrijavi": true
        }
      }

      body = {...body,
         "podnosilacAdresa": this.podnosilacAdresa,
         "pronalazacAdresa": this.podnosilacJePronalazacChecked ? this.podnosilacAdresa : this.pronalazacAdresa,
         "punomocAdresa": this.punomocAdresa,
         "podnosilacKontakt": this.podnosilacKontakt,
         "pronalazacKontakt": this.podnosilacJePronalazacChecked ? this.podnosilacKontakt : this.pronalazacKontakt,
         "punomocKontakt": this.punomocKontakt,
         "ranijePrijave": this.ranijePrijave,
         "adresaDostave": body.drugaAdresaDostave ?  this.adresaDostave : this.punomocAdresa
        }

      console.log(body);
      
      this.patentService.submitRequest(body).subscribe({
          next: data => {
            console.log(data);           
          },
          error: error => {
            console.error(error);
            }
        });
    }

    onTipLicaChangedPodnosilac(){
      this.fizikoLicePodnosilacChecked = !this.fizikoLicePodnosilacChecked;
    }

    onTipLicaChangedPronalazac(){
      this.fizikoLicePronalazacChecked = !this.fizikoLicePronalazacChecked;
    }

    onTipLicaChangedPunomoc(){
      this.fizikoLicePunomocChecked = !this.fizikoLicePunomocChecked;
    }

    onNacinDostaveChanged(){
      this.elektronskaDostavaChecked = !this.elektronskaDostavaChecked;
    }

    onTipPunomocnikaChanged(){
      this.tipPunomocnikaZaZastupanjeChecked = !this.tipPunomocnikaZaZastupanjeChecked;
    }

    onVrstaPrijaveChanged(){
      this.dopunskaPrijavaChecked = !this.dopunskaPrijavaChecked;
    }

    onNewRanijaPrijava(){
      this.ranijePrijave.push({
        datumPodnosenja: "",
        brojPrijave: 0,
        dvoslovnaOznaka: ""
      });
    }

    onDeleteRanijaPrijava(i: number){
        this.ranijePrijave.splice(i, 1);
    }

    // Address events
    onPodnosilacAdresa(event: Adresa){
      this.podnosilacAdresa = event;
      
    }

    onPronalazacAdresa(event: Adresa){
      this.pronalazacAdresa = event;
    }

    onPunomocAdresa(event: Adresa){
      this.punomocAdresa = event;
    }

    onAdresaDostave(event: Adresa){
      this.adresaDostave = event;
    }

    //Contact events
    onPodnosilacKontakt(event: Kontakt){
      this.podnosilacKontakt = event;
      
    }

    onPronalazacKontakt(event: Kontakt){
      this.pronalazacKontakt = event;
    }

    onPunomocKontakt(event: Kontakt){
      this.punomocKontakt = event;
    }

    onPodnosilacPronalazacChange(){      
      this.podnosilacJePronalazacChecked = !this.podnosilacJePronalazacChecked;
    }
}