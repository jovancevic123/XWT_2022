import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogResenjeData } from 'src/app/model/DialogResenjeData';
import { RequestListComponent } from '../request-list/request-list.component';

@Component({
  selector: 'app-conclusion-dialog',
  templateUrl: './conclusion-dialog.component.html',
  styleUrls: ['./conclusion-dialog.component.scss']
})
export class ConclusionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RequestListComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogResenjeData,
  ) {
    console.log(data);

  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
