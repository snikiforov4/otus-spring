import {Component, OnInit} from '@angular/core';
import {Tweet} from "../tweet";
import {TweetService} from "../tweet.service";
import {TweetDialogComponent} from "../tweet-dialog/tweet-dialog.component";
import {MatDialog} from "@angular/material";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-tweets-list',
  templateUrl: './tweets-list.component.html',
  styleUrls: ['./tweets-list.component.css']
})
export class TweetsListComponent implements OnInit {

  authorized: boolean = false;
  tweets: Array<Tweet>;

  constructor(public dialog: MatDialog,
              private tweetService: TweetService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.authorized = this.authService.isAuthorized();
    this.tweetService.getAll().subscribe(data => {
      this.tweets = data;
    });
  }

  addNewTweet(tweet: Tweet) {
    if (tweet) {
      this.tweets.unshift(tweet);
    }
  }

  updateTweet(tweet: Tweet) {
    if (tweet) {
      let idx = this.tweets.findIndex(e => e.id == tweet.id);
      if (idx > 0) {
        this.tweets[idx] = tweet;
      }
    }
  }

  deleteTweet(tweetId: string) {
    this.tweetService.delete(tweetId).subscribe(_ => {
      this.tweets = this.tweets.filter(e => e.id != tweetId);
    })
  }

  openEditTweetDialog(tweet: Tweet): void {
    const dialogRef = this.dialog.open(TweetDialogComponent, {
      width: '550px',
      data: {title: 'Edit Tweet', actionButtonText: 'Edit', tweet: tweet}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.tweetService.edit(result).subscribe(updatedTweet => {
          this.updateTweet(updatedTweet);
        })
      }
    });
  }

}
