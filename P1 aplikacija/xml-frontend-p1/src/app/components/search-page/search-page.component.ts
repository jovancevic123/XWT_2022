import { Component, OnInit } from '@angular/core';
import { AdvancedSearchMeta } from 'src/app/model/AdvancedSearchMeta';
import { SearchResult } from 'src/app/model/PendingRequest';
import { SearchService } from 'src/app/services/search.service';

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

const ELEMENT_DATA: SearchResult[] = [
  { brojPrijave: "123678", nazivPodnosioca: 'Jovan', nazivPatenta: "TV" }

];

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  protected vrstaPretrage: number = 2;
  basicSearchInput: string = "";
  advancedSearchInput: AdvancedSearchMeta[] = [];
  searchResults: SearchResult[];

  constructor(private searchService: SearchService){}
  
  ngOnInit(){
    this.searchResults = ELEMENT_DATA;
  }

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  onVrstaPretrageChanged(vrstaPretrage: number){
      this.advancedSearchInput = [];
      this.basicSearchInput = "";

      if(vrstaPretrage === 1){
          this.vrstaPretrage = 1;
      }else{
          this.vrstaPretrage = 2;
      } 
  }

  basicSearch(){    
    this.searchService.basicSearch(this.basicSearchInput).subscribe({
      next: res => {
          console.log(res);  
      },
      error: error => {
          console.error(error);
      }
    });
  }

  advancedSearch(){
      this.searchService.advancedSearch(this.advancedSearchInput).subscribe({
        next: res => {
            console.log(res);  
        },
        error: error => {
            console.error(error);
        }
      });
  }

  onDeleteAdvancedSeachInput(index: number){
    this.advancedSearchInput.splice(index, 1);
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
