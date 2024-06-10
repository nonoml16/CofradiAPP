import { Component, Input } from '@angular/core';
import { CardItemList } from '../../models/card-item-list';

@Component({
  selector: 'app-card-list-item',
  templateUrl: './card-list-item.component.html',
  styleUrl: './card-list-item.component.css',
})
export class CardListItemComponent {
  @Input() card!: CardItemList;

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
}
