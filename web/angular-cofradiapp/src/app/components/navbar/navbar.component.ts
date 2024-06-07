import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  isHermandadesRouteActive(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path === 'hermandades';
  }

  isBandasRouteActive(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path === 'bandas';
  }

  isCardsRouteActive(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path === 'cards';
  }

  isUsersRouteActive(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path === 'users';
  }

  isHomeRouteActive(): boolean {
    return this.route.snapshot.firstChild?.routeConfig?.path === 'home';
  }

}
