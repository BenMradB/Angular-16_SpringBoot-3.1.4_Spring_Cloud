import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  public user!: User;
  public err: number = 0;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  public register(user: User) {
    this.user = user;
    this.authService.register(this.user).subscribe({
      next: () => {
        alert(
          `Please checkout your email, verify your account than u'll be able to login and use our app`
        );
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.log(`Im Error : ${err.message}`);
        this.err = 1;
      },
    });
  }
}
