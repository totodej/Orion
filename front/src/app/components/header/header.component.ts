import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, map, Observable } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isLogged$: Observable<boolean>;
  showUserMenu = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { 
    this.isLogged$ = this.authService.isLogged$;
  }

  ngOnInit(): void {
    // On met à jour le bouton selon la route actuelle
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.router.url)
    ).subscribe(url => {
      this.showUserMenu = !!localStorage.getItem('token') 
        && !['/login', '/register'].includes(url);
    });

    // Écoute les changements d'état de connexion
    this.isLogged$.subscribe(isLogged => {
      const currentUrl = this.router.url;
      this.showUserMenu = isLogged && !['/login', '/register'].includes(currentUrl);
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/'])
  }

}
