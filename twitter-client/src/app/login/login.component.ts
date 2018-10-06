import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {FormBuilder, Validators} from "@angular/forms";
import {UserService} from "../user.service";
import {User} from "../user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private userService: UserService
  ) {
  }

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  model: any = {};

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  login() {
    let url = '/login';
    this.http.post<Observable<boolean>>(url, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(isValid => {
      if (isValid) {
        sessionStorage.setItem('token', btoa(this.model.username + ':' + this.model.password));
        this.router.navigateByUrl('');
      } else {
        alert("Authentication failed.")
      }
    });
  }

  onLogin() {
    // todo
    console.warn(this.loginForm.value);
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
