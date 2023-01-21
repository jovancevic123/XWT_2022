import { Injectable } from '@angular/core';

var convert = require('xml-js');

@Injectable({
  providedIn: 'root'
})
export class TokenUtilService {
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

  xml2Json(xml: any){
    let options = {ignoreComment: true, alwaysChildren: false, nativeType: false, alwaysArray: false, compact: true, textFn:this.removeJsonTextAttribute};
    return convert.xml2json(xml, options);
  }

  removeJsonTextAttribute(value:any, parentElement:any){
    try{
      var keyNo = Object.keys(parentElement._parent).length;
      var keyName = Object.keys(parentElement._parent)[keyNo-1];
      parentElement._parent[keyName] = value;
    }
      catch(e){}
    }
}
