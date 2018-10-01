import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCardModule, MatToolbarModule} from '@angular/material';
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {HttpClientModule} from '@angular/common/http';
import {TweetService} from "./tweet.service";
import {TweetsListComponent} from './tweets-list/tweets-list.component';
import {TweetDialogComponent} from './tweet-dialog/tweet-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    TweetsListComponent,
    TweetDialogComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule,
  ],
  entryComponents: [TweetDialogComponent],
  providers: [TweetService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
