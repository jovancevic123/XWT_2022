import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  @Output() form: FormGroup;

  ngOnInit(){
    this.form = new FormGroup({
      email: new FormControl('',[Validators.required]),
      broj: new FormControl('',[Validators.required]),
      fax: new FormControl('',[Validators.required]),
    });
  }
   

}
