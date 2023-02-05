import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogReferenceData } from 'src/app/model/DialogReferenceData';
import { RequestListComponent } from '../request-list/request-list.component';

@Component({
  selector: 'app-references',
  templateUrl: './references.component.html',
  styleUrls: ['./references.component.css']
})
export class ReferencesComponent {
  constructor(
    public dialogRef: MatDialogRef<RequestListComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogReferenceData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
