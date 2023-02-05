import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

  getPendingRequests(){
    return this.http.get(this.apiURL + '/a1/get-pending-requests', {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  getRequestHTML(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/a1/html', { params: queryParams, responseType:'text'});
  }

  getRequestPDF(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/a1/pdf', { params: queryParams, responseType: 'blob'});
  }

  approveRequest(broj: string){
    let body = {
      brojPrijave: broj,
      obrazlozenje: "",
      imeSluzbenika: this.tokenUtilService.getUserFromToken(), 
      prihvacena: true
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/a1/approve-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  rejectRequest(broj: string, obrazlozenje: string){
    let body = {
      brojPrijave: broj,
      obrazlozenje: obrazlozenje,
      imeSluzbenika: this.tokenUtilService.getUserFromToken(),
      prihvacena: false
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/a1/reject-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  generateReport(startDate: Date, endDate: Date){
      let startMonth = ('0'+(startDate.getMonth()+1)).slice(-2);
      let endMonth = ('0'+(endDate.getMonth()+1)).slice(-2);

      let startDay = ('0'+(startDate.getDate())).slice(-2);
      let endDay = ('0'+(endDate.getDate())).slice(-2);

      let startDateString: string = startDate.getFullYear() + "-" + startMonth + "-" + startDay; 
      let endDateString: string = endDate.getFullYear() + "-" + endMonth + "-" + endDay; 

      let queryParams = new HttpParams();
      queryParams = queryParams.append("start", startDateString);
      queryParams = queryParams.append("end", endDateString);

      return this.http.get(this.apiURL + '/a1/report', { params: queryParams, responseType: 'blob'});
  }

  getRequestRDF(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/a1/rdf', { params: queryParams, responseType: 'blob'});
  }

  getRequestJSON(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/a1/json', { params: queryParams, responseType: 'blob'});
  }

  dobaviReferencirane(brojPrijave: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojPrijave);

    return this.http.get(this.apiURL + '/a1/documents-are-referenced', { params: queryParams, responseType: 'text'});
  }

  dobaviReferencirajuce(brojPrijave: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojPrijave);

    return this.http.get(this.apiURL + '/a1/documents-that-reference', { params: queryParams, responseType: 'text'});
  }

  getUserRequests(email: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("email", email);

    return this.http.get(this.apiURL + '/a1/user-requests', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }
}
