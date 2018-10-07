import {Component, OnInit, ViewChild} from '@angular/core';
import {TweetsListComponent} from "../tweets-list/tweets-list.component";
import {MatDialog} from "@angular/material";
import {TweetService} from "../tweet.service";
import {TweetDialogComponent} from "../tweet-dialog/tweet-dialog.component";
import {Tweet} from "../tweet";
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  authorized: boolean = false;

  @ViewChild(TweetsListComponent)
  private tweetsListComponent: TweetsListComponent;

  constructor(public dialog: MatDialog,
              private tweetService: TweetService,
              private authService: AuthService,
              private router: Router) {
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

  ngOnInit(): void {
    this.authorized = this.authService.isAuthorized();
  }

  logout() {
    this.router.navigateByUrl('/login');
  }
}
