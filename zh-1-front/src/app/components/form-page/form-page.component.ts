import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Adresa } from 'src/app/model/Adresa';
import { Kontakt } from 'src/app/model/Kontakt';
import { ZigService } from 'src/app/services/zig.service';
import { ENTER, COMMA } from "@angular/cdk/keycodes";
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';

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

  constructor(private zigService: ZigService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      
      //podnosilac
      imePodnosioca: new FormControl('',[Validators.required]),
      prezimePodnosioca: new FormControl('',[Validators.required]),
      poslovnoImePodnosioca: new FormControl('',[Validators.required]),
      tipLicaPodnosilac: new FormControl('1',[Validators.required]),

      //punomoc
      imePunomocnika: new FormControl('',[Validators.required]),
      prezimePunomocnika: new FormControl('',[Validators.required]),
      poslovnoImePunomocnika: new FormControl('',[Validators.required]),
      tipLicaPunomocnik: new FormControl('1',[Validators.required]),

      //zig
      tipZiga: new FormControl('',[Validators.required]),

      //znak
      tipZnaka: new FormControl('',[Validators.required]),
      transliteracijaZnaka: new FormControl('',[Validators.required]),
      boje: new FormControl([], [Validators.required, Validators.minLength(1)]),
      prevodZnaka: new FormControl('',[Validators.required]), 
      opisZnaka: new FormControl('',[Validators.required]), 
      izgledZnaka: new FormControl('',[Validators.required]), 

      //ostalo
      brojeviKlasa: new FormControl([], [Validators.required, Validators.minLength(0)]),
      zatrazenoPravo: new FormControl('',[Validators.required]),
      placeneTakse: new FormControl([], [Validators.required, Validators.minLength(1)]),
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
       "placeneTakse": {"osnovna_taksa": this.osnovnaTaksaIznos.toString(), "taksa_za_graficko_resenje": this.taksaZaGrafickoResenjeIznos.toString()}
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

  onTipLicaChangedPodnosilac(){
    this.fizikoLicePodnosilacChecked = !this.fizikoLicePodnosilacChecked;
  }

  onTipLicaChangedPunomocnik(){
    this.fizikoLicePunomocnikChecked = !this.fizikoLicePunomocnikChecked;
  }

  // Address events
  onPodnosilacAdresa(event: Adresa){
    this.adresaPodnosioca = event;
  }

  onPunomocnikAdresa(event: Adresa){
    this.adresaPunomocnika = event;
  }

  //Contact events
  onPodnosilacKontakt(event: Kontakt){
    this.kontaktPodnosioca = event;
    
  }

  onPunomocKontakt(event: Kontakt){
    this.kontaktPunomocnika = event;
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
    this.ukupanIznosTaksi = this.osnovnaTaksaIznos + this.taksaZaGrafickoResenjeIznos;
  }
}
