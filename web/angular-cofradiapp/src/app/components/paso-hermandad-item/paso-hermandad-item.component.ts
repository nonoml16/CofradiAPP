import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Paso } from '../../models/hermandad-details';
import { Router } from '@angular/router';

@Component({
  selector: 'app-paso-hermandad-item',
  templateUrl: './paso-hermandad-item.component.html',
  styleUrl: './paso-hermandad-item.component.css',
})
export class PasoHermandadItemComponent {
  @Input() paso!: Paso;
  @Output() delete = new EventEmitter<string>();

  constructor(private modalService: NgbModal, private router: Router) {}

  onEdit(pasoId: string) {
    this.router.navigate([`/admin/paso/editar`, pasoId]);
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
    this.delete.emit(this.paso.id);
  }
}
