import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  logIn(credentials: FormGroup) : Observable<any> {

    let data = {
      email: credentials.value.email,
      password: credentials.value.password
    };
    
    let xmlZahtev = JsonToXML.parse("loginDto", data);    

    return this.http.post("http://localhost:8086/api/auth/login", xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }

  register(credentials: FormGroup) : Observable<any> {

    let data = {
      firstname: credentials.value.firstname,
      lastname: credentials.value.lastname,
      email: credentials.value.email,
      password: credentials.value.password,
      role: credentials.value.role,
    };
    
    let xmlZahtev = JsonToXML.parse("registerDto", data);    

    return this.http.post("http://localhost:8086/api/auth/register", xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }
}
