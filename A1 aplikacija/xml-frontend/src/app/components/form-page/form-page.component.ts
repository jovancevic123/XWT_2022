import { AutorskoDeloServiceService } from './../../services/autorsko-delo-service.service';

import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Adresa } from 'src/app/model/Adresa';
import { Autor } from 'src/app/model/Autor';
import { Kontakt } from 'src/app/model/Kontakt';

@Component({
  selector: 'app-form-page',
  templateUrl: './form-page.component.html',
  styleUrls: ['./form-page.component.scss']
})
export class FormPageComponent {
  form: FormGroup;

  prazanAutor:Autor = {
      kontakt:{
        email:'',
        broj:''
      },
      adresa:{
        ulica:'',
        broj:0,
        mesto:'',
        postanskiBroj:0,
        drzava:''
      },
      ime:'',
      prezime:'',
      drzavljanstvo:''
  }
  


  //adrese
  podnosilacAdresa: Adresa;
  pronalazacAdresa: Adresa;
  punomocAdresa: Adresa;

  //kontakti
  podnosilacKontakt: Kontakt;
  punomocnikKontakt: Kontakt;

  autoriDela: Autor[] = [JSON.parse(JSON.stringify(this.prazanAutor))]
  autoriDelaNum: number[] = [0];


  autoriDelaPrerade: Autor[] = [JSON.parse(JSON.stringify(this.prazanAutor))];
  autoriDelaPreradeNum: number[] = [0];

  constructor(private autorskoDeloService:AutorskoDeloServiceService){}

  ngOnInit(){
    this.form = new FormGroup({
      //podnosilac
      podnosilacJeAutor: new FormControl(false),
      tipLicaPodnosilac: new FormControl('fizicko',[Validators.required]),
      //fizicko
      imePodnosilac: new FormControl('',[Validators.required]),
      prezimePodnosilac: new FormControl('',[Validators.required]),
      drzavljanstvoPodnosilac: new FormControl('',[Validators.required]),
      //pravno 
      poslovnoImePodnosilac: new FormControl('',[Validators.required]),
      sedistePodnosilac: new FormControl('',[Validators.required]),

      prijavaPrekoPunomocnika:new FormControl(false,[Validators.required]),
      //punomocnik
      tipLicaPunomocnik: new FormControl('fizicko',[Validators.required]),
      //fizicko
      imePunomocnik: new FormControl('',[Validators.required]),
      prezimePunomocnik: new FormControl('',[Validators.required]),
      drzavljanstvoPunomocnik: new FormControl('',[Validators.required]),
      //pravno 
      poslovnoImePunomocnik: new FormControl('',[Validators.required]),
      sedistePunomocnik: new FormControl('',[Validators.required]),

      //autorsko delo
      naslovAutorskogDela: new FormControl('', [Validators.required]),
      vrstaDela: new FormControl('', [Validators.required]),
      formaDela: new FormControl('', [Validators.required]),
      anonimanAutor: new FormControl(false, [Validators.required]),
      uRadnomOdnosu: new FormControl(false, [Validators.required]),
      nacinKoriscenjaDela:new FormControl('', [Validators.required]),

      //delo prerade
      jeDeloPrerade:new FormControl(false,[Validators.required]),
      naslovDelaPrerade: new FormControl('', [Validators.required]),
      
      //ostalo
      vrstaPrijave: new FormControl('1'),
    });
  }

  onSubmit(){
    console.log(this.form.value);
    let data = this.form.value;
    let zahtev:any = {
      autorskoDelo:{
        naslovDela:data.naslovAutorskogDela,
        vrstaDela:data.vrstaDela,
        formaDela:data.formaDela,
        anonimanAutor:data.anonimanAutor,
        uRadnomOdnosu:data.uRadnomOdnosu,
        nacinKoriscenjaDela:data.nacinKoriscenjaDela,
        jeDeloPrerade:data.jeDeloPrerade,
      }
    };


    //provera lica podnosioca
    if(data.tipLicaPodnosilac === "fizicko"){
      zahtev.podnosilac = {
        ime:data.imePodnosilac,
        prezime:data.prezimePodnosilac,
        drzavljanstvo: data.drzavljanstvoPodnosilac,
        adresa:this.podnosilacAdresa,
        kontakt:this.podnosilacKontakt
      }
    }
    else{
      zahtev.podnosilac = {
        poslovnoIme:data.poslovnoImePodnosilac,
        sediste:data.sedistePodnosilac,
        kontakt:this.podnosilacKontakt
      }
    }


    //ako je punomocnik
    if(data.prijavaPrekoPunomocnika){
      if(data.tipLicaPunomocnik === "fizicko"){
        zahtev.punomocnik = {
          ime:data.imePunomocnik,
          prezime:data.prezimePunomocnik,
          drzavljanstvo: data.drzavljanstvoPunomocnik,
          adresa:this.punomocAdresa,
          kontakt:this.punomocnikKontakt
        }
      }
      else{
        zahtev.punomocnik = {
          poslovnoIme:data.poslovnoImePunomocnik,
          sediste:data.sedistePunomocnik,
          kontakt:this.punomocnikKontakt
        }
      }
    }

    //ako je autor anoniman
    if(!data.anonimanAutor){
      zahtev.autoriDela = this.autoriDela;
    }
    //ako je delo prerade
    if(data.jeDeloPrerade){
      zahtev.deloPrerade = {
        naslovDela:data.naslovDelaPrerade,
        autoriDelaPrerade:this.autoriDelaPrerade,
      }
    }

    console.log(zahtev);

    this.autorskoDeloService.submitRequest(zahtev).subscribe({
      next: data => {
        console.log(data);           
      },
      error: error => {
        console.error(error);
        }
    });

  }

  onPodnosilacAdresa(event: Adresa){
    this.podnosilacAdresa = event;
  }
  //Contact events
  onPodnosilacKontakt(event: Kontakt){
    this.podnosilacKontakt = event;
  }
  
  onPunomocnikAdresa(event: Adresa){
    this.podnosilacAdresa = event;
    
  }
  //Contact events
  onPunomocnikKontakt(event: Kontakt){
    this.punomocnikKontakt = event;
  }

  onAutorDelaPrerade(event: Autor, autorIndex:number){
    this.autoriDelaPrerade[autorIndex] = event;
    console.log(this.autoriDelaPrerade);
  }
  addAutorPrerade(){
    this.autoriDelaPreradeNum.push(this.autoriDelaPreradeNum.length);
    this.autoriDelaPrerade.push(JSON.parse(JSON.stringify(this.prazanAutor)));
  }
  removeAutorPrerade(){
    this.autoriDelaPrerade.pop();
    this.autoriDelaPreradeNum.pop();
  }
  

  onAutorDela(event: Autor, autorIndex:number){
    this.autoriDela[autorIndex] = event;
    console.log(this.autoriDela);
  }
  addAutor(){
    this.autoriDelaNum.push(this.autoriDelaNum.length);
    this.autoriDela.push(JSON.parse(JSON.stringify(this.prazanAutor)));
  }
  removeAutor(){
    this.autoriDela.pop();
    this.autoriDelaNum.pop();
  }

  get tipLicaPodnosilac(){
    return this.form.get("tipLicaPodnosilac")?.value;
  }
  
  get prijavaPrekoPunomocnika(){
    return this.form.get("prijavaPrekoPunomocnika")?.value;
  }
  
  get tipLicaPunomocnik(){
    return this.form.get("tipLicaPunomocnik")?.value;
  } 
  
  get jeDeloPrerade(){
    return this.form.get("jeDeloPrerade")?.value;
  }
  
  get podnosilacJeAutor(){
    return this.form.get("podnosilacJeAutor")?.value;
  }
  
  get anonimanAutor(){
    return this.form.get("anonimanAutor")?.value;
  }
  
}
