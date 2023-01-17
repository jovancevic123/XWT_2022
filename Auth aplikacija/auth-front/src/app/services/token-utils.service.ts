import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenUtilsService {

  constructor() { }

  getRoleFromToken(): string | null{
    try{
        let token: any = localStorage.getItem("user");
        return token.role;
    }
    catch{
        return null;
    }
  }

  getUserFromToken(): string | null {
    try{
        let token: any = localStorage.getItem("user");
        return token.firstname + " " + token.lastname;
    }
    catch{
        return null;
    }
  }
}
