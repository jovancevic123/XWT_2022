import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResenjeService {
  
  private apiURL: String = "http://localhost:8085/api";

  constructor(private http: HttpClient) { }

  getResenje(brojResenja: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojResenja);

    return this.http.get(this.apiURL + '/zh1/get-resenje', { params: queryParams, responseType: 'text'});
    
  }
}
