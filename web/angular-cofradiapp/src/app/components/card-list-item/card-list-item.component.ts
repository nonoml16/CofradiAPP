import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CardItemList } from '../../models/card-item-list';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-list-item',
  templateUrl: './card-list-item.component.html',
  styleUrl: './card-list-item.component.css',
})
export class CardListItemComponent {
  @Input() card!: CardItemList;

  @Output() delete = new EventEmitter<number>();

  constructor(private router: Router) {}

  getFormattedTipo(): string {
    const tipos: { [key: string]: string } = {
      CRUZ_DE_GUIA: 'Cruz de guía',
      INSIGNIA: 'Insignia',
      PASO: 'Paso',
      PALIO: 'Palio',
      CRISTO: 'Cristo',
      VIRGEN: 'Virgen',
      ESCUDO: 'Escudo',
      FLASH: 'Flash',
    };
    return tipos[this.card.tipoCard] || this.card.tipoCard;
  }

  onEdit(cardId: number) {
    this.router.navigate([`/admin/cards/editar`, cardId]);
  }

  onDelete() {
    this.delete.emit(this.card.id);
  }
}
