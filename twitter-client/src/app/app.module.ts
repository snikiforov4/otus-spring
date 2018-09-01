import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {TweetService} from "./tweet.service";
import {TweetsListComponent} from './tweets-list/tweets-list.component';

@NgModule({
  declarations: [
    AppComponent,
    TweetsListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [TweetService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
