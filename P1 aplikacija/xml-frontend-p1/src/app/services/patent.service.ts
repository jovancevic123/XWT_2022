import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as JsonToXML from "js2xmlparser";

@Injectable({
  providedIn: 'root'
})
export class PatentService {

  private apiURL: String = "http://localhost:8083/api";

  constructor(private http: HttpClient) { }


  submitRequest(request: any){
    
    const xmlZahtev = JsonToXML.parse("requestDto", request);
    console.log(xmlZahtev);

    return this.http.post(this.apiURL + '/p1/add-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  submitRequestXonomy(request: any){
    
    let xmlZahtev = JsonToXML.parse("xonomyRequestDto", request);
    console.log(xmlZahtev);

    // xmlZahtev = xmlZahtev.replaceAll("&lt;", "<");

    xmlZahtev = xmlZahtev.replaceAll(";fizicko_lice", ";lice xsi:type='TFizickoLice'");
    xmlZahtev = xmlZahtev.replaceAll(";pravno_lice", ";lice xsi:type='TPravnoLice'");

    xmlZahtev = xmlZahtev.replaceAll("/fizicko_lice", "/lice");
    xmlZahtev = xmlZahtev.replaceAll("/pravno_lice", "/lice");

    // xmlZahtev = xmlZahtev.replaceAll("<request", "<request xmlns='http://www.ftn.uns.ac.rs/xwt' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'");

    console.log(xmlZahtev);

    return this.http.post(this.apiURL + '/p1/add-request-xonomy', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  getPendingRequests(){
    return this.http.get(this.apiURL + '/p1/get-pending-requests', {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'})
  }

  getRequestHTML(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/p1/html', { params: queryParams, responseType:'text'});
  }

  getRequestPDF(broj: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("brojPrijave", broj);

    return this.http.get(this.apiURL + '/p1/pdf', { params: queryParams, responseType: 'blob' as 'json'});
  }

  approveRequest(broj: string){
    let body = {
      brojPrijave: broj,
      obrazlozenje: "",
      imeSluzbenika: "Marko",
      prezimeSluzbenika: "Rokvic"
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/p1/approve-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

  rejectRequest(broj: string, obrazlozenje: string){
    let body = {
      brojPrijave: broj,
      obrazlozenje: obrazlozenje,
      imeSluzbenika: "Marko",
      prezimeSluzbenika: "Rokvic"
    };

    let xmlZahtev = JsonToXML.parse("responseToPendingRequestDto", body);

    return this.http.post(this.apiURL + '/p1/reject-request', xmlZahtev, {headers: new HttpHeaders().set('Content-Type', 'application/xml'), responseType:'text'});
  }

}
