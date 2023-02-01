import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Adresa } from 'src/app/model/Adresa';
import { Kontakt } from 'src/app/model/Kontakt';
import { ZigService } from 'src/app/services/zig.service';
import { ENTER, COMMA } from "@angular/cdk/keycodes";
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';
import { PlacenaTaksa } from 'src/app/model/PlacenaTaksa';

@Component({
  selector: 'app-form-page',
  templateUrl: './form-page.component.html',
  styleUrls: ['./form-page.component.css']
})
export class FormPageComponent implements OnInit {

  form: FormGroup;

  //adrese
  adresaPodnosioca: Adresa;
  adresaPunomocnika: Adresa;

  addressPodnosilacFormValid: boolean = false;
  contactPodnosilacFormValid: boolean = false;

  addressPunomocnikFormValid: boolean = false;
  contactPunomocnikFormValid: boolean = false;

  //kontakti
  kontaktPodnosioca: Kontakt;
  kontaktPunomocnika: Kontakt;

  fizikoLicePodnosilacChecked: boolean = true;
  fizikoLicePunomocnikChecked: boolean = true;

  tipZiga: string;
  tipZnaka: string;

  tipoviZiga: string[] = ['Individualni zig', 'Kolektivni zig', 'Zig garancije']
  tipoviZnaka: string[] = ['Verbalni znak', 'Graficki znak', 'Trodimenzionalni znak', 'Druga vrsta znaka']

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  boje: string[] = [];

  brojeviKlasa: number[] = [];

  osnovnaTaksaIznos: number;
  taksaZaGrafickoResenjeIznos: number;
  ukupanIznosTaksi: number;

  imePodnosioca: string;

  formIsValid: boolean;

  constructor(private zigService: ZigService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      
      //podnosilac
      imePodnosioca: new FormControl(),
      prezimePodnosioca: new FormControl(),
      poslovnoImePodnosioca: new FormControl(),
      tipLicaPodnosilac: new FormControl('1',[Validators.required]),

      //punomoc
      imePunomocnika: new FormControl(),
      prezimePunomocnika: new FormControl(),
      poslovnoImePunomocnika: new FormControl(),
      tipLicaPunomocnik: new FormControl('1',[Validators.required]),

      //zig
      tipZiga: new FormControl('',[Validators.required]),

      //znak
      tipZnaka: new FormControl('',[Validators.required]),
      transliteracijaZnaka: new FormControl('',[Validators.required]),
      prevodZnaka: new FormControl('',[Validators.required]), 
      opisZnaka: new FormControl('',[Validators.required]), 
      // izgledZnaka: new FormControl('',[Validators.required]), 

      //ostalo
      zatrazenoPravo: new FormControl('',[Validators.required]),
      osnovnaTaksaIznos: new FormControl('',[Validators.required]),
      taksaZaGrafickoResenjeIznos: new FormControl('',[Validators.required]),
    });
  }

  get tipLicaPodnosilac(){
    return this.form.get("tipLicaPodnosilac");
  }

  get tipLicaPunomocnik(){
    return this.form.get("tipLicaPunomocnik");
  }

  onSubmit(){
    let body = this.form.getRawValue();

    body = {...body,
       "adresaPodnosioca": this.adresaPodnosioca,
       "adresaPunomocnika": this.adresaPunomocnika,
       "kontaktPodnosioca": this.kontaktPodnosioca,
       "kontaktPunomocnika": this.kontaktPunomocnika,
       "tipZiga": this.tipZiga,
       "tipZnaka": this.tipZnaka,
       "boje": this.boje,
       "brojeviKlasa": this.brojeviKlasa,
       "placeneTakse": [
          new PlacenaTaksa("Osnovna taksa", this.form.getRawValue()["osnovnaTaksaIznos"]),
          new PlacenaTaksa("Taksa za graficko resenje", this.form.getRawValue()["taksaZaGrafickoResenjeIznos"]),
       ],
       "tipLicaPodnosilac": body.tipLicaPodnosilac,
       "tipLicaPunomocnik": body.tipLicaPunomocnik
      }
    this.zigService.submitRequest(body).subscribe({
        next: data => {
          console.log(data);           
        },
        error: error => {
          console.error(error);
        }
      });
  }

  onTipLicaChangedPodnosilacToFizicko(){
    this.fizikoLicePodnosilacChecked = true;
  }

  onTipLicaChangedPodnosilacToPravno()
  {
    this.fizikoLicePodnosilacChecked = false;
  }
  

  onTipLicaChangedPunomocnikToFizicko(){
    this.fizikoLicePodnosilacChecked = true;
  }
  
  onTipLicaChangedPunomocnikToPravno()
  {
    this.fizikoLicePunomocnikChecked = false;
  }
  

  // Address events
  onPodnosilacAdresa(event: Adresa){
    this.adresaPodnosioca = event;
    if(this.adresaPodnosioca.broj != null && this.adresaPodnosioca.drzava != "" && this.adresaPodnosioca.mesto != "" && this.adresaPodnosioca.postanskiBroj != null && this.adresaPodnosioca.postanskiBroj >= 11000 && this.adresaPodnosioca.ulica != "")
    {
      this.addressPodnosilacFormValid = true;
    }
    else{
      this.addressPodnosilacFormValid = false;
    }
  }

  onPunomocnikAdresa(event: Adresa){
    this.adresaPunomocnika = event;
    if(this.adresaPunomocnika.broj != null && this.adresaPunomocnika.drzava != "" && this.adresaPunomocnika.mesto != "" && this.adresaPunomocnika.postanskiBroj != null && this.adresaPunomocnika.postanskiBroj >= 11000 && this.adresaPunomocnika.ulica != "")
    {
      this.addressPunomocnikFormValid = true;
    }
    else{
      this.addressPunomocnikFormValid = false;
    }
  }

  //Contact events
  onPodnosilacKontakt(event: Kontakt){
    this.kontaktPodnosioca = event;
    if(this.kontaktPodnosioca.broj !== null && this.kontaktPodnosioca.email !== "" && this.kontaktPodnosioca.fax !== "")
    {
      this.contactPodnosilacFormValid = true;
    }
    else{
      this.contactPodnosilacFormValid = false;
    }
  }

  onPunomocKontakt(event: Kontakt){
    this.kontaktPunomocnika = event;
    if(this.kontaktPunomocnika.broj != null && this.kontaktPunomocnika.email != "" && this.kontaktPunomocnika.fax != "")
    {
      this.contactPunomocnikFormValid = true;
    }
    else{
      this.contactPunomocnikFormValid = false;
    }
  }

  onFileSelected(event: Event, fileInput: HTMLInputElement){
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    const file: File = fileList?.item(0) as File;
    fileInput.value = "";
    
    const reader = new FileReader();
    reader.readAsDataURL(file);
    // reader.onload = () => {
    //   this.profilePicture = "data:image/jpg;base64, " + reader.result;

    //   this.userService.changeProfilePicture(this.loggedUser?.email as string, reader.result)
    //   .subscribe({
    //       next: (response: string) => {
    //         this.toastr.success("You have successfully updated profile picture!");
            
    //         this.profilePicture = "data:image/png;base64, " + response;
    //       },
    //       error: (err: HttpErrorResponse) => {
    //         this.toastr.warning(err.error);
    //       }
    //     }
    //   );
    // };    
  }

  addColor(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      
      this.boje.push(value);
    }
    event.chipInput!.clear();
  }

  removeColor(color: string): void {
    const index = this.boje.indexOf(color);

    if (index >= 0) {
      this.boje.splice(index, 1);
    }
  }

  editColor(color: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    if (!value) {
      this.removeColor(color);
      return;
    }

    const index = this.boje.indexOf(color);
    if (index > 0) {
      this.boje[index] = value;
    }
  }

  updateSelectedNumbers(number: number, isChecked: boolean) {
    if (isChecked) {
      this.brojeviKlasa.push(number);
    } else {
      this.brojeviKlasa = this.brojeviKlasa.filter(n => n !== number);
    }
    console.log(this.brojeviKlasa);
  }

  calculateTotalPrice()
  {
    this.ukupanIznosTaksi = this.form.getRawValue()["taksaZaGrafickoResenjeIznos"] + this.form.getRawValue()["osnovnaTaksaIznos"];
    console.log(this.form)
    console.log(this.addressPodnosilacFormValid)
    console.log(this.addressPunomocnikFormValid);
    console.log(this.contactPodnosilacFormValid)
    console.log(this.contactPunomocnikFormValid)
  }
}
