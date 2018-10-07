import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {FormBuilder, Validators} from "@angular/forms";
import {UserService} from "../user.service";
import {User} from "../user";
import {TokenHolder} from "../token-holder";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private userService: UserService,
    private authService: AuthService
  ) {
  }

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  ngOnInit() {
    this.authService.removeToken();
  }

  onLogin() {
    let url = '/auth';
    let user = new User(this.username.value, this.password.value);
    this.http.post<TokenHolder>(url, user).subscribe(token => {
      if (token) {
        sessionStorage.setItem('token', token.token);
        this.router.navigateByUrl('');
      } else {
        console.warn("Authentication failed.")
      }
    });
  }

  onRegistration() {
    let user = new User(this.username.value, this.password.value);
    this.userService.register(user).subscribe(user => {
      console.log('User has been successfully registered: {}', user);
    });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

}
