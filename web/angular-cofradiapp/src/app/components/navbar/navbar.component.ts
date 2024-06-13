import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { UserService } from '../../services/user.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  currentUser: any;
  imageUrl!: SafeUrl;

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}
  ngOnInit(): void {
    this.userService.getAuthUserLite().subscribe(
      (user) => {
        this.currentUser = user;
        this.imagenService.getImage(user.imagenPerfil).subscribe(
          (response) => {
            const objectURL = URL.createObjectURL(response);
            this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
          },
          (error) => {
            console.error('Error al cargar la imagen:', error);
          }
        );
      },
      (error) => {
        console.error('Error fetching user data', error);
      }
    );
  }

  logout(): void {
    this.authService.logout();
  }

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
