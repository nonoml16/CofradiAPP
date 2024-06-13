import { Component, Input, OnInit } from '@angular/core';
import { CardsDestacada } from '../../models/home';
import { ImagenService } from '../../services/imagen.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-home-card-component',
  templateUrl: './home-card-component.component.html',
  styleUrl: './home-card-component.component.css',
})
export class HomeCardComponentComponent implements OnInit {
  @Input() card!: CardsDestacada;

  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.imagenService.getImage(this.card.imagen).subscribe(
      (response) => {
        const objectURL = URL.createObjectURL(response);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      (error) => {
        console.error('Error al cargar la imagen:', error);
      }
    );
  }
}
