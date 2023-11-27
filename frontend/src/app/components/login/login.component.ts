import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public user!: User;
  public wrongCredentials: string = '';
  public err: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  public onLoggedIn(user: User) {
    this.user = user;
    this.userService.getUserByEmail(user.username).subscribe({
      next: (_user: User) => {
        if (!_user.isEnabled) {
          this.err =
            'Account may not be enabled. verify your email inbox you may find a verification mail';
          return;
        }

        this.authService.login(this.user).subscribe({
          next: (data) => {
            let jwtToken = data.headers.get('Authorization')!;
            this.authService.saveToken(jwtToken);
            localStorage.setItem('verifiedLogin', 'true');
            localStorage.setItem('loggedUser', user.username);
            this.router.navigate(['/']);
          },
          error: (err: any) => {
            console.log(err);
            this.err = 'Wrong email or password';
          },
        });
      },
      error: (err: any) => {
        console.log(err);
        this.err = err.message;
      },
    });
  }
}
