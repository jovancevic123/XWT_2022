import { TokenUtilService } from './../../services/token-util.service';
import { AutorskoDeloServiceService } from './../../services/autorsko-delo-service.service';

import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Adresa } from 'src/app/model/Adresa';
import { Autor } from 'src/app/model/Autor';
import { Kontakt } from 'src/app/model/Kontakt';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

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

  constructor(private autorskoDeloService:AutorskoDeloServiceService, private toastr:ToastrService, private router:Router){}

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

      prijavaPrekoPunomocnika:new FormControl(false),
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
    const validForm = this.checkIfFormIsValid(data);
    if(!validForm){
      console.log("Usao")
      this.toastr.warning("All fields must be valid", "Form Invalid");
      return;
    }
    let zahtev:any = {
      autorskoDelo:{
        naslovDela:data.naslovAutorskogDela,
        vrstaDela:data.vrstaDela,
        formaDela:data.formaDela,
        anonimanAutor:data.anonimanAutor,
        uRadnomOdnosu:data.uRadnomOdnosu,
        nacinKoriscenjaDela:data.nacinKoriscenjaDela,
        jeDeloPrerade:data.jeDeloPrerade,
        podnosilacJeAutor:data.podnosilacJeAutor
      }
    };


    //provera lica podnosioca
    if(data.tipLicaPodnosilac === "fizicko"){
      const valid = this.checkIfPodnosilacFizickoLiceIsValid(data);
      if(!valid){
        this.toastr.warning("All fields in Podnosilac must be valid", "Form Invalid");
        return;
      }
      zahtev.podnosilac = {
        ime:data.imePodnosilac,
        prezime:data.prezimePodnosilac,
        drzavljanstvo: data.drzavljanstvoPodnosilac,
        adresa:this.podnosilacAdresa,
        kontakt:this.podnosilacKontakt
      }
    }
    else{
      console.log(data);
      console.log(this.podnosilacKontakt);
      const valid = this.checkIfPodnosilacPravnoLiceIsValid(data);
      if(!valid){
        this.toastr.warning("All fields in Podnosilac must be valid", "Form Invalid");
        return;
      }
      zahtev.podnosilac = {
        poslovnoIme:data.poslovnoImePodnosilac,
        sediste:data.sedistePodnosilac,
        kontakt:this.podnosilacKontakt
      }
    }


    //ako je punomocnik
    if(data.prijavaPrekoPunomocnika){
      if(data.tipLicaPunomocnik === "fizicko"){
        const valid = this.checkIfPunomocnikFizickoLiceIsValid(data);
        if(!valid){
          this.toastr.warning("All fields in Punomocnik must be valid", "Form Invalid");
          return;
        }
        zahtev.punomocnik = {
          ime:data.imePunomocnik,
          prezime:data.prezimePunomocnik,
          drzavljanstvo: data.drzavljanstvoPunomocnik,
          adresa:this.punomocAdresa,
          kontakt:this.punomocnikKontakt
        }
      }
      else{
        const valid = this.checkIfPunomocnikPravnoLiceIsValid(data);
        if(!valid){
          this.toastr.warning("All fields in Punomocnik must be valid", "Form Invalid");
          return;
        }
        zahtev.punomocnik = {
          poslovnoIme:data.poslovnoImePunomocnik,
          sediste:data.sedistePunomocnik,
          kontakt:this.punomocnikKontakt
        }
      }
    }

    //ako autor nije anoniman
    if(!data.anonimanAutor && !data.podnosilacJeAutor){
      const valid = this.checkIfAutorsAreValid();
      if(!valid){
        this.toastr.warning("All fields in Autor must be valid", "Form Invalid");
        return;
      }
      zahtev.autorskoDelo.autoriDela = this.autoriDela;
    }
    //ako je delo prerade
    if(data.jeDeloPrerade){
      const valid = this.checkIfDeloPreradeIsValid(data);
      if(!valid){
        this.toastr.warning("All fields in Delo prerade must be valid", "Form Invalid");
        return;
      }
      zahtev.deloPrerade = {
        naslovDela:data.naslovDelaPrerade,
        autoriDelaPrerade:this.autoriDelaPrerade,
      }
    }

    this.autorskoDeloService.submitRequest(zahtev).subscribe({
      next: data => {
        console.log(data);
        this.toastr.success("","Successfully saved");
        this.router.navigate(['/user', {outlets: {'user-outlet': ['search']}}]);
      },
      error: error => {
        console.error(error);
        this.toastr.error(error.error,"Error While Saving")
        }
    });

  }

  checkIfFormIsValid(data:any){
    if(!data.naslovAutorskogDela || !data.vrstaDela || !data.formaDela || !data.nacinKoriscenjaDela)
      return false;
    return true;
  }

  checkIfPodnosilacPravnoLiceIsValid(data:any){
    if(!data.poslovnoImePodnosilac || !data.sedistePodnosilac || !this.podnosilacKontakt.broj || !this.podnosilacKontakt.email)
      return false;
    return true;
  }

  checkIfPodnosilacFizickoLiceIsValid(data:any){
    if(!data.imePodnosilac || !data.prezimePodnosilac || !data.drzavljanstvoPodnosilac || !this.podnosilacAdresa.broj || !this.podnosilacAdresa.drzava 
      || !this.podnosilacAdresa.mesto || !this.podnosilacAdresa.postanskiBroj || !this.podnosilacAdresa.ulica)
      return false
    return true;

  }

  checkIfPunomocnikPravnoLiceIsValid(data:any){
    if(!data.poslovnoImePunomocnik || !data.sedistePunomocnik || !this.punomocnikKontakt.broj || !this.punomocnikKontakt.email)
      return false;
    return true;
  }

  checkIfPunomocnikFizickoLiceIsValid(data:any){
    if(!data.imePunomocnik || !data.prezimePunomocnik || !data.drzavljanstvoPunomocnik || !this.punomocAdresa.broj || !this.punomocAdresa.drzava 
      || !this.punomocAdresa.mesto ||!this.punomocAdresa.postanskiBroj ||!this.punomocAdresa.ulica )
      return false;
    return true;
  }

  checkIfDeloPreradeIsValid(data:any){
    if(!data.naslovDelaPrerade)
      return false;

    for (const autor of this.autoriDelaPrerade) {
      if(!autor.ime || !autor.prezime || !autor.drzavljanstvo || !autor.kontakt.email || !autor.kontakt.broj || !autor.adresa.broj || !autor.adresa.drzava 
        || !autor.adresa.mesto || !autor.adresa.postanskiBroj || !autor.adresa.ulica)
        return false;
    }
    return true;

  }

  checkIfAutorsAreValid(){
    for (const autor of this.autoriDela) {
      console.log(autor);
      if(!autor.ime || !autor.prezime || !autor.drzavljanstvo || !autor.kontakt.email || !autor.kontakt.broj || !autor.adresa.broj || !autor.adresa.drzava 
        || !autor.adresa.mesto || !autor.adresa.postanskiBroj || !autor.adresa.ulica)
        return false;
    }
    return true;
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
