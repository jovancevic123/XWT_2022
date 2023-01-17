import { AuthService } from './../../services/auth.service';

import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { catchError, lastValueFrom, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TokenUtilsService } from 'src/app/services/token-utils.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  loginForm: FormGroup;

  constructor(private authService: AuthService, private router: Router, private tokenUtilsService: TokenUtilsService) {}
  
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
        .pipe(catchError(err => {return throwError(() => {new Error('greska')} )}))
        .subscribe({
          next: (res) => {
            let token = res.accessToken;
            localStorage.setItem("user", token);
            let role: string | null = this.tokenUtilsService.getRoleFromToken();            
          },
          error: (err) => {
            console.log(err.error);
          },
        });
  }

  goToRegistration():void{
    this.router.navigateByUrl('/registration');
  }
}
