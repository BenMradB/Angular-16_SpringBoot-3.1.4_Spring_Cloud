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
    this.authService.login(this.user).subscribe({
      next: (data) => {
        let jwtToken = data.headers.get('Authorization')!;
        this.authService.saveToken(jwtToken);
        // this.userService
        //   .getUserByEmail(this.authService.decodeJWT().sub)
        //   .subscribe({
        //     next: (user: User) => {
        //       if (!user.isEnabled) {
        //         this.err = 'Account disabled';
        //         this.router.navigate(['/login']);
        //         return;
        //       }
        //       localStorage.setItem('isEnabled', 'true');
        //       this.router.navigate(['/']);
        //     },
        //     error: (err: any) => {
        //       console.log(err);
        //     },
        //   });
        this.router.navigate(['/']);
      },
      error: (err: any) => {
        console.log(err);
        this.err = 'Wrong email or password';
      },
    });
  }
}
