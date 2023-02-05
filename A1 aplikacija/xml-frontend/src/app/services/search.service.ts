import { TokenUtilService } from './token-util.service';
import { AdvancedSearchMeta } from './../model/AdvancedSearchMeta';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as JsonToXML from "js2xmlparser";


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private apiURL: String = "http://localhost:8084/api";

  constructor(private http: HttpClient, private tokenUtilService:TokenUtilService) { }


  basicSearch(textToSearch: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("textToSearch", textToSearch);
    
    return this.http.get(this.apiURL + '/a1/basic-search', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }
  
  basicSearchForUser(textToSearch: string){
    let userEmail = this.tokenUtilService.getEmailFromToken();
    let queryParams = new HttpParams();
    queryParams = queryParams.append("textToSearch", textToSearch);
    queryParams = queryParams.append("email", userEmail as string);
    
    return this.http.get(this.apiURL + '/a1/basic-search-user', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }

  advancedSearch(meta: AdvancedSearchMeta[]){
    let dto: any = {};
    dto.conditions = [];

    for(let m of meta){
        dto.conditions.push(m);
    }
    
    let xmlZahtev = JsonToXML.parse("advancedSearchListDto", dto);

    return this.http.post(this.apiURL + '/a1/advanced-search', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
  
  advancedSearchForUser(meta: AdvancedSearchMeta[]){
    let dto: any = {};

    let userCondition:AdvancedSearchMeta = {
      meta: 'podnosilac_email',
      value: this.tokenUtilService.getEmailFromToken() as string,
      operator: 'I'
    }
    dto.conditions = [userCondition];

    for(let m of meta){
        dto.conditions.push(m);
    }
    
    let xmlZahtev = JsonToXML.parse("advancedSearchListDto", dto);

    return this.http.post(this.apiURL + '/a1/advanced-search-user', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
}
