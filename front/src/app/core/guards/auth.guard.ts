import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService, 
    private router: Router
  ) {}
  
  public canActivate(): boolean {
    const token = localStorage.getItem('token');

    if (token) {
      return true; // utilisateur connecté
    }

    // sinon redirection
    this.router.navigate(['/login']);
    return false;
  }
  
}
