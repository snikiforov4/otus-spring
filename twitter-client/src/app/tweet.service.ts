import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tweet} from "./tweet";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  private tweetUrl = '/tweet/';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.tweetUrl);
  }

  save(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>(this.tweetUrl, tweet);
  }

  edit(tweet: Tweet): Observable<Tweet> {
    return this.http.put<Tweet>(this.tweetUrl, tweet)
      .pipe(
        tap(e => console.log(`edit tweet=${e}`)),
      );
  }

  delete(tweet: Tweet | string): Observable<Tweet> {
    const id = typeof tweet === 'string' ? tweet : tweet.id;
    const url = `${this.tweetUrl}${id}`;
    return this.http.delete<Tweet>(url)
      .pipe(
        tap(_ => console.log(`deleted tweet id=${id}`)),
      );
  }

}
