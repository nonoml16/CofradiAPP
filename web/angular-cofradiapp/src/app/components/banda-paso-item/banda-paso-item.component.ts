import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Acompannamiento } from '../../models/paso-details';
import { Router } from '@angular/router';

@Component({
  selector: 'app-banda-paso-item',
  templateUrl: './banda-paso-item.component.html',
  styleUrl: './banda-paso-item.component.css',
})
export class BandaPasoItemComponent {
  @Input() musica!: Acompannamiento;
  @Output() delete = new EventEmitter<string>();

  constructor(private modalService: NgbModal, private router: Router) {}

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
    this.delete.emit(this.musica.id);
  }
}
