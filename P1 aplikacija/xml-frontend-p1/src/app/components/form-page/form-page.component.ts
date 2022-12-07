import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-form-page',
  templateUrl: './form-page.component.html',
  styleUrls: ['./form-page.component.css']
})
export class FormPageComponent implements OnInit{

    form: FormGroup;
    fizikoLicePodnosilacChecked: boolean = true;
    fizikoLicePronalazacChecked: boolean = true;
    fizikoLicePunomocChecked: boolean = true;
    elektronskaDostavaChecked: boolean = false;
    dopunskaPrijavaChecked: boolean = true;

    constructor(){}

    ngOnInit(){
      this.form = new FormGroup({
        tipLica: new FormControl('1',[Validators.required]),
      });
    }

    get tipLica(){
      return this.form.get("tipLica");
    }
    
    onSubmit(){}

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

    onVrstaPrijaveChanged(){
      this.dopunskaPrijavaChecked = !this.dopunskaPrijavaChecked;
    }
}
