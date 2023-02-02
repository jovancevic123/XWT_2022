import { Component, OnInit } from '@angular/core';
import { TokenUtilService } from 'src/app/services/token-util.service';

@Component({
  selector: 'app-korisnik-dashboard',
  templateUrl: './korisnik-dashboard.component.html',
  styleUrls: ['./korisnik-dashboard.component.scss']
})
export class KorisnikDashboardComponent implements OnInit{
  role:string;

  constructor(private tokenUtilService: TokenUtilService){}

  ngOnInit(){
    let role: string | null = this.tokenUtilService.getRoleFromToken();
  }

}
