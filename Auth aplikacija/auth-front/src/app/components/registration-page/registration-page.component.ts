import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { catchError, lastValueFrom, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TokenUtilsService } from 'src/app/services/token-utils.service';
import { AuthService } from './../../services/auth.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent {

  registerForm: FormGroup;

  constructor(private authService: AuthService, private router: Router, private tokenUtilsService: TokenUtilsService) {}

  ngOnInit() {
    this.registerForm = new FormGroup({
        'email': new FormControl('', Validators.required),
        'password': new FormControl('', Validators.required),
        'firstname': new FormControl('', Validators.required),
        'lastname': new FormControl('', Validators.required),
        'role': new FormControl('1', Validators.required),
    });
  }

  get formFields() { return this.registerForm.controls; }
  
  onSubmit() { 
      localStorage.clear();

        this.authService.register(this.registerForm);
        // .pipe(catchError(err => {return throwError(() => {new Error('greska')} )}))
        // .subscribe({
        //   next: (res) => {
        //     let token = res.accessToken;
        //     localStorage.setItem("user", token);
        //     let role: string | null = this.tokenUtilsService.getRoleFromToken();            
        //   },
        //   error: (err) => {
        //     console.log(err.error);
        //   },
        // });
  }

  goToRegistration():void{
    this.router.navigateByUrl('/registration');
  }
}
