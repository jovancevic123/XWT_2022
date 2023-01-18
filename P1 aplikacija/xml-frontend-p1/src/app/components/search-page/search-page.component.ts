import { Component } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent {

  protected vrstaPretrage: number = 1;
  basicSearchInput: string = "";

  constructor(private searchService: SearchService){}

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  onVrstaPretrageChanged(vrstaPretrage: number){
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

  }
}
