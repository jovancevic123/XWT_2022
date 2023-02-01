import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class ZigService {
  
  private apiURL: String = "http://localhost:8085/api";

  constructor(private http: HttpClient) { }

  submitRequest(request: any){
    
    const xmlZahtev = JsonToXML.parse("requestDto", request);
    console.log(xmlZahtev);

    return this.http.post(this.apiURL + '/zh1/add-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  getRequestHTML(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijaveZiga", broj);

    return this.http.get(this.apiURL + '/z1/html', { params: queryParams, responseType:'text'});
  }

  getRequestPDF(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijaveZiga", broj);

    return this.http.get(this.apiURL + '/zh1/pdf', { params: queryParams, responseType: 'blob'});
  }

  approveRequest(broj: string){
    let body = {
      brojPrijaveZiga: broj,
      obrazlozenje: "",
      imeSluzbenika: "Nevena Simic", // this.tokenUtilService.getUserFromToken(),
      prihvacena: true
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/zh1/approve-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  rejectRequest(broj: string, obrazlozenje: string){
    let body = {
      brojPrijaveZiga: broj,
      obrazlozenje: obrazlozenje,
      imeSluzbenika: "Nevena Simic", //this.tokenUtilService.getUserFromToken(),
      prihvacena: false
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/zh1/reject-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  getRequestRDF(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijaveZiga", broj);

    return this.http.get(this.apiURL + '/zh1/rdf', { params: queryParams, responseType: 'blob'});
  }

  getRequestJSON(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijaveZiga", broj);

    return this.http.get(this.apiURL + '/zh1/json', { params: queryParams, responseType: 'blob'});
  }

  dobaviReferencirane(brojPrijaveZiga: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojPrijaveZiga);

    return this.http.get(this.apiURL + '/zh1/documents-are-referenced', { params: queryParams, responseType: 'text'});
  }

  dobaviReferencirajuce(brojPrijaveZiga: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("documentId", brojPrijaveZiga);

    return this.http.get(this.apiURL + '/zh1/documents-that-reference', { params: queryParams, responseType: 'text'});
  }

  getUserRequests(email: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("email", email);

    return this.http.get(this.apiURL + '/zh1/user-requests', {params: queryParams, headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  getPendingRequests(){
    return this.http.get(this.apiURL + '/zh1/get-pending-requests', {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
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

    return this.http.get(this.apiURL + '/zh1/report', { params: queryParams, responseType: 'blob'});
}
}
