import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Card } from '../../models/hermandad-details';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-hermandad-item',
  templateUrl: './card-hermandad-item.component.html',
  styleUrl: './card-hermandad-item.component.css',
})
export class CardHermandadItemComponent {
  @Input() card!: Card;
  @Output() delete = new EventEmitter<number>();

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
    this.delete.emit(this.card.id);
  }
}
