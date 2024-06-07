import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({

  providedIn: 'root'

})
export class AuthGuard implements CanActivate {

  constructor (public _authService: AuthService,

  public router: Router) {}

  canActivate(

    next: ActivatedRouteSnapshot,

    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

      if (!this._authService.isLoggedIn()) {

        this.router.navigate(['login']);

        return false;

      }

      return true;

  }

}
