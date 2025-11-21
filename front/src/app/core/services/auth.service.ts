import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest } from '../models/loginRequest';
import { RegisterRequest } from '../models/registerRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:3001/api/auth';

  private isLoggedSubject = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  public isLogged$ = this.isLoggedSubject.asObservable();

  constructor(private http: HttpClient) { }

  public login(request: LoginRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, request).pipe(
      tap((response: any) => {
        localStorage.setItem('token', response.token);
        this.isLoggedSubject.next(true);
      })
    );
  }

  public logout(): void {
    localStorage.removeItem('token');
    this.isLoggedSubject.next(false);
  }

  register(request: RegisterRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, request);
  }

}
