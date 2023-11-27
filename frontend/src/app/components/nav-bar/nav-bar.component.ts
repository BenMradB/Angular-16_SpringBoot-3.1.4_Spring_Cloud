import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
})
export class NavBarComponent implements OnInit {
  public username: string = 'Connect';
  public userLogged: boolean = localStorage.getItem('loggedUser') !== null;

  constructor(private router: Router, public authService: AuthService) {}
  ngOnInit(): void {
    if (
      this.authService.getToken() === null ||
      this.authService.isTokenExpired() ||
      !!!localStorage.getItem('verifiedLogin')
    ) {
      this.router.navigate(['/login']);
    } else {
      this.authService.loadToken();
      this.userLogged = true;
      this.username = localStorage.getItem('loggedUser')!;
      this.router.navigate(['/']);
    }
  }

  public logout(): void {
    this.userLogged = false;
    this.username = 'Connect';
    this.authService.logout();
  }
}
