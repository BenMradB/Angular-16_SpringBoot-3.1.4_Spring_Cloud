import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from 'src/app/models/User.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiServerUrl = `${environment.apiBaseUrl}`;
  private helper = new JwtHelperService();

  public token!: string;
  public username!: string;
  public roles!: string[];
  public isLoggedIn: boolean = false;
  public loggedUser!: string;

  constructor(private router: Router, private http: HttpClient) {}

  register(user: User) {
    return this.http.post(`${this.apiServerUrl}/api/register`, user, {
      observe: 'response',
    });
  }

  login(user: User) {
    return this.http.post<User>(`${this.apiServerUrl}/login`, user, {
      observe: 'response',
    });
  }

  saveToken(jwt: string) {
    localStorage.setItem('jwt', jwt);
    this.token = jwt;
    this.isLoggedIn = true;
    this.decodeJWT();
  }

  loadToken(): void {
    this.token = localStorage.getItem('jwt') || '';
  }

  getToken(): string {
    return this.token;
  }

  decodeJWT(): any {
    if (this.token == undefined) return;
    const decodedToken = this.helper.decodeToken(this.token);
    this.roles = decodedToken?.roles;
    this.loggedUser = decodedToken?.sub;
    return decodedToken;
  }

  isAdmin(): Boolean {
    if (!this.roles) return false;
    return this.roles.indexOf('ADMIN') >= 0;
  }

  logout() {
    this.loggedUser = undefined!;
    this.roles = undefined!;
    this.token = undefined!;
    this.isLoggedIn = false;
    localStorage.removeItem('jwt');
    localStorage.removeItem('verifiedLogin');
    localStorage.removeItem('loggedUser');
    this.router.navigate(['/login']);
  }

  isTokenExpired(): Boolean {
    return this.helper.isTokenExpired(this.token);
  }
}
