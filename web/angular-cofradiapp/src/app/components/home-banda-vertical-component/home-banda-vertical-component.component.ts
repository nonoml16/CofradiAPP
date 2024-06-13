import { Component, Input } from '@angular/core';
import { MusicasDestacada } from '../../models/home';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-home-banda-vertical-component',
  templateUrl: './home-banda-vertical-component.component.html',
  styleUrl: './home-banda-vertical-component.component.css',
})
export class HomeBandaVerticalComponentComponent {
  @Input() banda!: MusicasDestacada;

  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}
  ngOnInit(): void {
    this.imagenService.getImage('null').subscribe(
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
