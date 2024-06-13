import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HermandadesFavorita } from '../../models/user-details';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-hermandad-user-item',
  templateUrl: './hermandad-user-item.component.html',
  styleUrl: './hermandad-user-item.component.css',
})
export class HermandadUserItemComponent {
  @Input() hermandad!: HermandadesFavorita;
  @Output() delete = new EventEmitter<string>();

  imageUrl!: SafeUrl;

  constructor(
    private modalService: NgbModal,
    private router: Router,
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

  openDeleteModal(content: any): void {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          if (result === 'confirm') {
            this.confirmDelete();
          }
        },
        (reason) => {
          // Manejar el cierre del modal sin eliminar
        }
      );
  }

  confirmDelete(): void {
    this.delete.emit(this.hermandad.id);
  }
}
