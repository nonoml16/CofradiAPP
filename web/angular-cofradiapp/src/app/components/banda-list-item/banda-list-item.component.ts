import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BandaItemList } from '../../models/banda-item-list';

@Component({
  selector: 'app-banda-list-item',
  templateUrl: './banda-list-item.component.html',
  styleUrl: './banda-list-item.component.css',
})
export class BandaListItemComponent {
  @Input() banda!: BandaItemList;
  @Output() delete = new EventEmitter<string>();

  getFormattedTipo(): string {
    const tipos: { [key: string]: string } = {
      BANDA_MUSICA: 'Banda de música',
      BANDA_CCTT: 'Banda de cornetas y tambores',
      AGRUPACION_MUSICAL: 'Agrupación Musical',
      MUSICA_CAPILLA: 'Musica de capilla',
      CORO: 'Coro',
    };
    return tipos[this.banda.tipo] || this.banda.tipo;
  }

  getFormattedNumHdades(): string {
    return this.banda.numHermandades === 1
      ? '1 hermandad'
      : `${this.banda.numHermandades} hermandades`;
  }

  onDelete() {
    this.delete.emit(this.banda.id);
  }
}
