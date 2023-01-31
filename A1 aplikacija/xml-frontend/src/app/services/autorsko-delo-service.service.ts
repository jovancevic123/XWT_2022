import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as JsonToXML from "js2xmlparser";
import { TokenUtilService } from './token-util.service';

@Injectable({
  providedIn: 'root'
})
export class AutorskoDeloServiceService {
  private apiURL: String = "http://localhost:8084/api";


  constructor(private http: HttpClient, private tokenUtilService: TokenUtilService) { }


  submitRequest(request: any){
    
    const xmlZahtev = JsonToXML.parse("zahtevRequestDto", request);
    console.log(xmlZahtev);

    return this.http.post(this.apiURL + '/a1/add-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
}
