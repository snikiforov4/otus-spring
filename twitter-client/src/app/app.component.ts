import {Component, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material";
import {TweetDialogComponent} from "./tweet-dialog/tweet-dialog.component";
import {Tweet} from "./tweet";
import {TweetService} from "./tweet.service";
import {TweetsListComponent} from "./tweets-list/tweets-list.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  @ViewChild(TweetsListComponent)
  private tweetsListComponent: TweetsListComponent;

  constructor(public dialog: MatDialog, private tweetService: TweetService) {
  }

  openComposeNewTweetDialog(): void {
    const dialogRef = this.dialog.open(TweetDialogComponent, {
      width: '550px',
      data: {title: 'Compose new Tweet', actionButtonText: 'Tweet', tweet: new Tweet()}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.tweetService.save(result).subscribe(savedTweet => {
          this.tweetsListComponent.addNewTweet(savedTweet);
          console.log('Tweet was successfully added: {}', savedTweet);
        })
      }
    });
  }

}
