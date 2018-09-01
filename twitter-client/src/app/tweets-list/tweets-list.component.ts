import {Component, OnInit} from '@angular/core';
import {Tweet} from "../tweet";
import {TweetService} from "../tweet.service";

@Component({
  selector: 'app-tweets-list',
  templateUrl: './tweets-list.component.html',
  styleUrls: ['./tweets-list.component.css']
})
export class TweetsListComponent implements OnInit {

  tweets: Array<Tweet>;

  constructor(private tweetsService: TweetService) { }

  ngOnInit() {
    this.tweetsService.getAll().subscribe(data => {
      this.tweets = data;
    });
  }

}
