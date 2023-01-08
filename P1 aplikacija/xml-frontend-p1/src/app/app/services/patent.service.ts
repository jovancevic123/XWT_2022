import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class PatentService {

  apiURL: String = "http://localhost:8083/api";

  constructor(private http: HttpClient) { }


  submitRequest(request: any){
    
    const xmlZahtev = JsonToXML.parse("requestDto", request);
    console.log(xmlZahtev);

    return this.http.post(this.apiURL + '/p1/add-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
}
