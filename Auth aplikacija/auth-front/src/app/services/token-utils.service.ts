import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenUtilsService {

  constructor() { }

  getRoleFromToken(): string | null{
    try{
        let token: any = JSON.parse(localStorage.getItem("user") as string);        
        return token.role;
    }
    catch{
        return null;
    }
  }

  getUserFromToken(): string | null {
    try{
        let token: any = JSON.parse(localStorage.getItem("user") as string);
        return token.firstname + " " + token.lastname;
    }
    catch{
        return null;
    }
  }
}
