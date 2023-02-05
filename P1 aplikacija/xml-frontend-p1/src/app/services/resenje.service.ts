import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class ResenjeService {

  private apiURL: String = "http://localhost:8083/api";

  constructor(private http: HttpClient) { }

  getResenje(brojResenja: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojResenja);

    return this.http.get(this.apiURL + '/p1/get-resenje', { params: queryParams, responseType: 'text'});
    
  }
}
