import { Component, OnInit } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../services/user.service';
import { Home } from '../../models/home';

@Component({
  selector: 'app-page-home',
  templateUrl: './page-home.component.html',
  styleUrl: './page-home.component.css',
})
export class PageHomeComponent implements OnInit {
  showNavigationArrows = false;
  showNavigationIndicators = false;
  images: string[] = [
    'https://www.diariodesevilla.es/2023/04/06/semana_santa/Imagenes-Quinta-Angustia_1781534450_181364839_1024x682.jpg',
    'https://www.diariodesevilla.es/2023/04/06/semana_santa/Imagenes-Quinta-Angustia_1781534425_181363639_1024x682.jpg',
    'https://www.diariodesevilla.es/2023/04/06/semana_santa/Imagenes-Quinta-Angustia_1781534455_181365080_1024x682.jpg',
    'https://www.diariodesevilla.es/2023/04/06/semana_santa/Imagenes-Quinta-Angustia_1781534464_181365512_1024x682.jpg',
  ];
  home!: Home;

  constructor(config: NgbCarouselConfig, private userService: UserService) {
    config.showNavigationArrows = true;
    config.showNavigationIndicators = true;
  }
  ngOnInit(): void {
    this.loadHomeData();
  }

  loadHomeData(): void {
    this.userService.getHome().subscribe((data) => {
      this.home = data;
    });
  }
}
