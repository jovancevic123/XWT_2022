import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private apiURL: String = "http://localhost:8083/api";

  constructor(private http: HttpClient) { }


  basicSearch(textToSearch: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("textToSearch", textToSearch);
    
    return this.http.get(this.apiURL + '/p1/basic-search', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }
}
