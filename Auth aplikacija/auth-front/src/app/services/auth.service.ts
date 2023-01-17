import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  logIn(credentials: FormGroup) : Observable<any> {

    let data = {
      email: credentials.value.email,
      password: credentials.value.password
    }    

    return this.http.post("http://localhost:8085/api/auth/login", data)
  }

  register(credentials: FormGroup) : Observable<any> {

    let data = {
      email: credentials.value.email,
      password: credentials.value.password,
      firstname: credentials.value.firstname,
      lastname: credentials.value.lastname,
      role: credentials.value.role,
    }    

    console.log(data);

    return this.http.post("http://localhost:8085/api/auth/register", data)
  }
}
