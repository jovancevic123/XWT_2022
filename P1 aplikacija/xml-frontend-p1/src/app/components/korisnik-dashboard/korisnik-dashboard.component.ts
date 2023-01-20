import { Component } from '@angular/core';
import { TokenUtilService } from 'src/app/services/token-util.service';

@Component({
  selector: 'app-korisnik-dashboard',
  templateUrl: './korisnik-dashboard.component.html',
  styleUrls: ['./korisnik-dashboard.component.css']
})
export class KorisnikDashboardComponent {

  patentLink: string;
  currentPage: number = 0;
  otherPageName: string = "Xonomy forma";

  constructor(private tokenUtilService: TokenUtilService){}

  ngOnInit(){
    let role: string | null = this.tokenUtilService.getRoleFromToken();        
  
    if(role === "KORISNIK"){
      this.patentLink = "http://localhost:4200/korisnik-dashboard";
    }else{
      this.patentLink = "http://localhost:4200/sluzbenik-dashboard";
    }
  }

  changePage(){
    this.currentPage = 1 - this.currentPage;
    this.otherPageName = this.currentPage == 0 ? "Xonomy forma" : "Regularna forma";
}

  logout(){
    localStorage.clear();
    window.location.href="http://localhost:4205/login";
  }

}
