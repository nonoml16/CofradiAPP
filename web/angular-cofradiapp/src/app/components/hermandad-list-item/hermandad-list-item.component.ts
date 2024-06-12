import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HermandadItemList } from '../../models/hermandad-item-list';
import { ImagenService } from '../../services/imagen.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hermandad-list-item',
  templateUrl: './hermandad-list-item.component.html',
  styleUrl: './hermandad-list-item.component.css',
})
export class HermandadListItemComponent implements OnInit {
  @Input() hermandad!: HermandadItemList;
  @Output() delete = new EventEmitter<string>();

  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer,
    private router: Router
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

  getFormattedNumPasos(): string {
    return this.hermandad.numPasos === 1
      ? '1 paso'
      : `${this.hermandad.numPasos} pasos`;
  }

  getFormattedDia(): string {
    const dias: { [key: string]: string } = {
      VIERNES_DOLORES: 'Viernes de Dolores',
      SABADO_PASION: 'Sábado de Pasión',
      DOMINGO_DE_RAMOS: 'Domingo de Ramos',
      LUNES_SANTO: 'Lunes Santo',
      MARTES_SANTO: 'Martes Santo',
      MIERCOLES_SANTO: 'Miércoles Santo',
      JUEVES_SANTO: 'Jueves Santo',
      VIERNES_SANTO_MADRUGA: 'Viernes Santo (Madrugá)',
      VIERNES_SANTO: 'Viernes Santo',
      SABADO_SANTO: 'Sábado Santo',
      DOMINGO_RESURRECCION: 'Domingo de Resurrección',
    };
    return dias[this.hermandad.dia] || this.hermandad.dia;
  }

  onEdit(hermandadId: string) {
    this.router.navigate([`/admin/hermandades/editar`, hermandadId]);
  }

  onDelete() {
    this.delete.emit(this.hermandad.id);
  }
}
