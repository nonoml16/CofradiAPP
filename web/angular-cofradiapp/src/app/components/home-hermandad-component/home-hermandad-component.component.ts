import { Component, Input, OnInit } from '@angular/core';
import { HermandadDia } from '../../models/home';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-home-hermandad-component',
  templateUrl: './home-hermandad-component.component.html',
  styleUrl: './home-hermandad-component.component.css',
})
export class HomeHermandadComponentComponent implements OnInit {
  @Input() hermandad!: HermandadDia;
  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}
  ngOnInit(): void {
    this.imagenService.getImage(this.hermandad.escudo).subscribe(
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
