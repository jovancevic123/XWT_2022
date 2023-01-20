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
    // const options = { // set up the default options 
    //   textKey: 'text', // tag name for text nodes
    //   attrKey: 'attr', // tag for attr groups
    //   cdataKey: 'cdata', // tag for cdata nodes (ignored if mergeCDATA is true)
    // };

    // let token = this.ngxXmlToJsonService.xmlToJson(xml, options);
    let options = {ignoreComment: true, alwaysChildren: false, nativeType: false, alwaysArray: false, compact: true, textFn:this.RemoveJsonTextAttribute};
    return convert.xml2json(xml, options);
  }

  RemoveJsonTextAttribute(value:any, parentElement:any){
    try{
    var keyNo = Object.keys(parentElement._parent).length;
    var keyName = Object.keys(parentElement._parent)[keyNo-1];
    parentElement._parent[keyName] = value;
    }
    catch(e){}
    }
}
