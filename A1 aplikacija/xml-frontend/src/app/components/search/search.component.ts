import { AutorskoDeloServiceService } from './../../services/autorsko-delo-service.service';
import { Component, Input, OnInit } from '@angular/core';
import { AdvancedSearchMeta } from 'src/app/model/AdvancedSearchMeta';
import { SearchResult } from 'src/app/model/SearchResult';
import { SearchService } from 'src/app/services/search.service';
import { TokenUtilService } from 'src/app/services/token-util.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit{

  
  startingList: SearchResult[] = [];
  @Input() isUser: boolean;

  protected vrstaPretrage: number = 1;
  basicSearchInput: string = "";
  advancedSearchInput: AdvancedSearchMeta[] = [];
  searchResults: SearchResult[];

  constructor(private searchService: SearchService, private autorskoDeloService:AutorskoDeloServiceService, private tokenUtilService: TokenUtilService){}

  ngOnInit(): void {
    this.basicSearch();
    // this.autorskoDeloService.getPendingRequests().subscribe({
    //   next: res => {
    //     console.log(res);
    //     this.startingList = this.makeJsonListOutOfSearchResults(res);
    //   },
    //   error: error => {
    //       console.error(error);
    //   }
    // });
  }
  
  basicSearch(){    
    this.searchService.basicSearch(this.basicSearchInput).subscribe({
      next: res => {
        console.log(res);
        this.searchResults = this.makeJsonListOutOfSearchResults(res);       
      },
      error: error => {
          console.error(error);
      }
    });
  }

  advancedSearch(){
    this.searchService.advancedSearch(this.advancedSearchInput).subscribe({
      next: res => {
        this.searchResults = this.makeJsonListOutOfSearchResults(res);
        console.log(this.searchResults);
      },
      error: error => {
          console.error(error);
      }
    });
  }

  makeJsonListOutOfSearchResults(xmlString: string): any {
    let results = JSON.parse(this.tokenUtilService.xml2Json(xmlString)).searchResultsDto.results;     
    console.log(results);
    
    if(!results){
      return [];
    }
    
    if(results.length){
      results = results;
    }
    results = [results];      

    if(results[0].length){
      for(let s of results[0]){
          if(typeof(s.brojResenja) !== 'string'){
            s.brojResenja = "";
          }
      }
      return results[0];
    }else if(typeof(results[0].brojResenja) !== 'string'){
        results[0].brojResenja = "";
    }

    console.log(results);
    
    return results;
  }

  onDeleteAdvancedSeachInput(index: number){
    this.advancedSearchInput.splice(index, 1);
  }

  onVrstaPretrageChanged(vrstaPretrage: number){
    this.advancedSearchInput = [];
    this.basicSearchInput = "";

    if(vrstaPretrage === 1){
        this.vrstaPretrage = 1;
    }else{
        this.vrstaPretrage = 2;
    } 
  }

  onNewUslov(){
    let newRow = {
      meta: "",
      value: "",
      operator: ""
    };
    
    this.advancedSearchInput.push(newRow);
  }
}
