import {HttpClient, HttpResponse} from "@angular/common/http";
import {Injectable} from '@angular/core';
import { Router } from "@angular/router";
import {Observable} from "rxjs";
import {User} from "src/app/authentication/User";
import {environment} from "src/environments/environment";


@Injectable()
export class AuthenticationServices {
  constructor(private http: HttpClient, private router: Router) {
  }

  AUTH_URL = 'http://tododaily-env.eba-k5ma8bqh.us-east-2.elasticbeanstalk.com';
//Principal
  username: string;
  roles: string = localStorage.getItem('roles');


  login(user: User): Observable<HttpResponse<Object>> {
    return this.http.post<HttpResponse<Object>>(`${this.AUTH_URL}/login?username=${user.username}&password=${user.password}`, user, {observe: 'response'});
  }

  signup(user: User): Observable<HttpResponse<Object>> {
    return this.http.post(`${this.AUTH_URL}/signup`, user, {observe: 'response'});
  }

  logout(): void {
    localStorage.clear();
  }

  savePrincipal(jwtToken: string, username: string, roles): void {
    localStorage.setItem('jwtToken', jwtToken);
    localStorage.setItem('username', username);
    localStorage.setItem('roles', roles);
    this.roles = roles;
  }

  isAuthenticated(): boolean {
    return (localStorage.getItem('jwtToken') != null);
  }

  hasRole(role: string): boolean {
      return this.roles.search(role) != -1;
  }
}
