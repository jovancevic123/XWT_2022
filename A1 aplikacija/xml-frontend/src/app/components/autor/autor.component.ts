import { Autor } from 'src/app/model/Autor';
import { Component, EventEmitter, NO_ERRORS_SCHEMA, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Adresa } from 'src/app/model/Adresa';
import { Kontakt } from 'src/app/model/Kontakt';

@Component({
  selector: 'app-autor',
  templateUrl: './autor.component.html',
  styleUrls: ['./autor.component.scss']
})
export class AutorComponent {
  @Output() formRawValue = new EventEmitter<Autor>();
  form: FormGroup; 

  autorAdresa: Adresa;

  //kontakti
  autorKontakt: Kontakt;

  ngOnInit(){
    this.form = new FormGroup({
      ime: new FormControl('',[Validators.required]),
      prezime: new FormControl('',[Validators.required]),
      drzavljanstvo: new FormControl('',[Validators.required]),
      pseudonim:new FormControl(''),
    });
  }
  
  onChange(){
    let data:Autor = this.form.getRawValue();
    data.adresa = this.autorAdresa;
    data.kontakt = this.autorKontakt;

    this.formRawValue.emit(data);
  }

  onAutorAdresa(event: Adresa){
    this.autorAdresa = event;
    this.onChange();
  }
  //Contact events
  onAutorKontakt(event: Kontakt){
    this.autorKontakt = event;
    this.onChange();
  }
}
