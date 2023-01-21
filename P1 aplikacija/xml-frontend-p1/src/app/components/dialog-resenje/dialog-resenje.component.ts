import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RequestListComponent } from '../request-list/request-list.component';
import { DialogResenjeData } from '../sluzbenik-dashboard/sluzbenik-dashboard.component';

@Component({
  selector: 'app-dialog-resenje',
  templateUrl: './dialog-resenje.component.html',
  styleUrls: ['./dialog-resenje.component.css']
})
export class DialogResenjeComponent {
    constructor(
      public dialogRef: MatDialogRef<RequestListComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogResenjeData,
    ) {}

    onNoClick(): void {
      this.dialogRef.close();
    }
}
