import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit{

    @Output() form: FormGroup;

    ngOnInit(){
      this.form = new FormGroup({
        ulica: new FormControl('',[Validators.required]),
        broj: new FormControl('',[Validators.required]),
        postanskiBroj: new FormControl('',[Validators.required]),
        mesto: new FormControl('',[Validators.required]),
        drzava: new FormControl('',[Validators.required]),
      });
    }
   
}
