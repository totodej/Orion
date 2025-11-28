import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { LoginRequest } from '../models/loginRequest';
import { RegisterRequest } from '../models/registerRequest';
import { UpdateUserRequest } from '../models/updateUserRequest';

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

  me(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/me`);
  }

  updateMe(request: UpdateUserRequest): Observable<User> {
    return this.http.put<{token: string, user: User }>(
      `${this.apiUrl}/me`,
      request
    ).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
      }),
      // Extract only the user from the response
      map(response => response.user)
    );
  }

}
