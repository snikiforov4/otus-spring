import {Component} from '@angular/core';
import {MatDialog} from "@angular/material";
import {TweetDialogComponent} from "./tweet-dialog/tweet-dialog.component";
import {Tweet} from "./tweet";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(public dialog: MatDialog) {}

  openComposeNewTweetDialog(): void {
    const dialogRef = this.dialog.open(TweetDialogComponent, {
      width: '350px',
      data: {title: 'Compose new Tweet', actionButtonText: 'Tweet', tweet: new Tweet()}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('New Tweet was successfully added');
      console.log(result);
    });
  }

}
