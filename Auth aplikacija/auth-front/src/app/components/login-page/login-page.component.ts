import { AuthService } from './../../services/auth.service';

import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { catchError, lastValueFrom, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TokenUtilsService } from 'src/app/services/token-utils.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  loginForm: FormGroup;

  constructor(private authService: AuthService, private router: Router, private tokenUtilsService: TokenUtilsService,
              private toastService: ToastrService/*, private ngxXmlToJsonService: NgxXmlToJsonService*/) {}
  
  ngOnInit() {
      this.loginForm = new FormGroup({
          'email': new FormControl('', Validators.required),
          'password': new FormControl('', Validators.required)
      });
      localStorage.removeItem("user");
  }
  
  get formFields() { return this.loginForm.controls; }
  
  onSubmit() { 
      localStorage.clear();

        this.authService.logIn(this.loginForm)
        .subscribe({
          next: (res) => {            
            // let token = this.xml2Json(res);   
            var parseString = require('xml2js').parseString;
            var that = this;
            parseString(res, function (err:any, result:any) {
              console.dir(result);
              let token = {
                    firstname: result.User.firstname[0],
                    lastname: result.User.lastname[0],
                    email: result.User.email[0],
                    password: result.User.password[0],
                    role: result.User.role[0],
              };

              localStorage.setItem("user", JSON.stringify(token));
              that.toastService.success("Successful login!");
              that.router.navigateByUrl("service-picker");

            });

            
            // window.location.href="http://localhost:4205/service-picker";
          },
          error: (err) => {
            this.toastService.error(err.error);
          },
        });
  }

  goToRegistration():void{
    this.router.navigateByUrl('/registration');
  }

}
