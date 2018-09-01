import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tweet} from "./tweet";

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>('//localhost:8080/tweet');
  }

}
