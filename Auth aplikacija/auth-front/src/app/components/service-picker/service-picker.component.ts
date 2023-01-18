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

    firstChosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();        
  
      if(role === "KORISNIK"){
        window.location.href = "http://localhost:4200/korisnik-dashboard";
      }else{
        window.location.href = "http://localhost:4200/sluzbenik-dashboard";
      }
      
    }

    secondChosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();        
  
      if(role === "KORISNIK"){
        window.location.href = "";
      }else{
        window.location.href = "";
      }
      
    }

    thirdChosen(){
      let role: string | null = this.tokenUtilsService.getRoleFromToken();        

      if(role === "KORISNIK"){
        window.location.href = "";
      }else{
        window.location.href = "";
      }

    }

    logout(){
      console.log("a");
      
      localStorage.clear();
      window.location.href="http://localhost:4201/login";       
    }

}
