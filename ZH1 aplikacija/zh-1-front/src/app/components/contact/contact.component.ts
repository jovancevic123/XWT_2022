import { Component, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Kontakt } from 'src/app/model/Kontakt';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  @Output() formRawValue = new EventEmitter<Kontakt>();
  @Output() form: FormGroup;

  ngOnInit(){
    this.form = new FormGroup({
      email: new FormControl('',[Validators.required, Validators.email]),
      broj: new FormControl('',[Validators.required]),
      fax: new FormControl('',[Validators.required]),
    });
  }

  onChange(){
    this.formRawValue.emit(this.form.getRawValue());
  } 
}