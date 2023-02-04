import { Component } from '@angular/core';
import { TokenUtilsService } from 'src/app/services/token-utils.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-service-picker',
  templateUrl: './service-picker.component.html',
  styleUrls: ['./service-picker.component.css']
})
export class ServicePickerComponent {


  constructor(private router: Router, private tokenUtilsService: TokenUtilsService,
    private toastService: ToastrService) {}

    patentServiceChoosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();        
  
      if(role === "KORISNIK"){
        window.location.href = "http://localhost:4200/korisnik-dashboard";
      }else{
        window.location.href = "http://localhost:4200/sluzbenik-dashboard";
      }
    }

    zigServiceChoosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();  
      let userEmail = this.tokenUtilsService.getUserEmailFromToken();      
      if(role === "KORISNIK"){
        window.location.href = `http://localhost:4202/korisnik-dashboard/${userEmail}`;
      }else{
        window.location.href = `http://localhost:4202/sluzbenik-dashboard/${userEmail}`;
      }
    }

    autorskoDeloServiceChoosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();    
      let userEmail = this.tokenUtilsService.getUserEmailFromToken();
      console.log(userEmail);
      window.location.href = `http://localhost:4201/user/${userEmail}/(user-outlet:form)`;
    }

    logout(){
      console.log("a");
      
      localStorage.clear();
      window.location.href="http://localhost:4205/login";       
    }

}
