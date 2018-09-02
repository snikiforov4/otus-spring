import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {MatButtonModule, MatCardModule, MatToolbarModule} from '@angular/material';
import {MatIconModule} from '@angular/material/icon';
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
    HttpClientModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
  ],
  providers: [TweetService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
