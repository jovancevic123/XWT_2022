import { AuthService } from './../../services/auth.service';

import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { catchError, lastValueFrom, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TokenUtilsService } from 'src/app/services/token-utils.service';
import { ToastrService } from 'ngx-toastr';
import * as JsonToXML from "js2xmlparser";
import { NgxXmlToJsonService } from 'ngx-xml-to-json';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  loginForm: FormGroup;

  constructor(private authService: AuthService, private router: Router, private tokenUtilsService: TokenUtilsService,
              private toastService: ToastrService, private ngxXmlToJsonService: NgxXmlToJsonService) {}
  
  ngOnInit() {
      this.loginForm = new FormGroup({
          'email': new FormControl('', Validators.required),
          'password': new FormControl('', Validators.required)
      });
  }
  
  get formFields() { return this.loginForm.controls; }
  
  onSubmit() { 
      localStorage.clear();

        this.authService.logIn(this.loginForm)
        .subscribe({
          next: (res) => {            
            let token = this.xml2Json(res);            
            localStorage.setItem("user", JSON.stringify(token));
            this.toastService.success("Successful login!");
            window.location.href="http://localhost:4201/service-picker";
          },
          error: (err) => {
            this.toastService.error(err.error);
          },
        });
  }

  goToRegistration():void{
    this.router.navigateByUrl('/registration');
  }

  xml2Json(xml: any){
    const options = { // set up the default options 
      textKey: 'text', // tag name for text nodes
      attrKey: 'attr', // tag for attr groups
      cdataKey: 'cdata', // tag for cdata nodes (ignored if mergeCDATA is true)
    };

    let token = this.ngxXmlToJsonService.xmlToJson(xml, options);

    return {
      firstname: token.User.firstname.text,
      lastname: token.User.lastname.text,
      email: token.User.email.text,
      password: token.User.password.text,
      role: token.User.role.text,
    };
  }

}
