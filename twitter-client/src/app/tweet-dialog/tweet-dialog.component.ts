import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {TweetDialogData} from "../tweet-dialog-data";

@Component({
  selector: 'app-tweet-dialog',
  templateUrl: './tweet-dialog.component.html',
  styleUrls: ['./tweet-dialog.component.css']
})
export class TweetDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<TweetDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TweetDialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
