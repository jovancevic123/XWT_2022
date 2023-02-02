import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AdvancedSearchMeta } from '../model/AdvancedSearchMeta';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private apiURL: String = "http://localhost:8085/api";

  constructor(private http: HttpClient) { }


  basicSearch(textToSearch: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("textToSearch", textToSearch);
    
    return this.http.get(this.apiURL + '/zh1/basic-search', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }

  advancedSearch(meta: AdvancedSearchMeta[]){
    let dto: any = {};
    dto.conditions = [];

    for(let m of meta){
        dto.conditions.push(m);
    }
    
    let xmlZahtev = JsonToXML.parse("advancedSearchListDto", dto);

    return this.http.post(this.apiURL + '/p1/advanced-search', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
}