import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { TokenUtilService } from 'src/app/services/token-util.service';

@Component({
  selector: 'app-korisnik-dashboard',
  templateUrl: './korisnik-dashboard.component.html',
  styleUrls: ['./korisnik-dashboard.component.scss']
})
export class KorisnikDashboardComponent implements OnInit{
  role:string;
  email:string|null;

  constructor(private tokenUtilService: TokenUtilService, private route:ActivatedRoute){}

  ngOnInit(){
    this.email = this.route.snapshot?.paramMap?.get('email')
    console.log(this.email);
    if(this.email && this.email != 'a'){
      this.tokenUtilService.setUser(this.email).subscribe({
        next: (res: any) => {
          console.log(res); 
          var parseString = require('xml2js').parseString;
          var that = this;
          parseString(res, function (err:any, result:any) {
            console.dir(result);
            let token = {
                  firstname: result.User.firstname[0],
                  lastname: result.User.lastname[0],
                  email: result.User.email[0],
                  password: result.User.password[0],
                  role: result.User.role[0],
            };
            localStorage.setItem("user", JSON.stringify(token));
            that.role = token.role;
          });
        },
        error: (error: any) => {
            console.error(error);
        }
      });}
    else{
      this.role = this.tokenUtilService.getRoleFromToken() as string;
    }

    // let role: string | null = this.tokenUtilService.getRoleFromToken();
    // console.log(role);
  }

  logOut(){
    localStorage.removeItem("user");
    window.location.href = "http://localhost:4205/login";
  }

}
