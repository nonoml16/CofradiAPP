import { Component, Input, OnInit } from '@angular/core';
import { HermandadesDestacada } from '../../models/home';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-home-hermandad-vertical-component',
  templateUrl: './home-hermandad-vertical-component.component.html',
  styleUrl: './home-hermandad-vertical-component.component.css',
})
export class HomeHermandadVerticalComponentComponent implements OnInit {
  @Input() hermandad!: HermandadesDestacada;

  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}
  ngOnInit(): void {
    this.imagenService.getImage(this.hermandad.escudo!).subscribe(
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
