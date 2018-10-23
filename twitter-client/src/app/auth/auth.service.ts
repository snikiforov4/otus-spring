import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenKey: string = 'token';

  constructor() { }

  isAuthorized(): boolean {
    let authorized = false;
    if (sessionStorage.getItem(this.tokenKey)) {
      authorized = true;
    }
    return authorized;
  }

  removeToken() {
    sessionStorage.removeItem(this.tokenKey);
  }


  getToken(): string {
    return sessionStorage.getItem(this.tokenKey);
  }
}
