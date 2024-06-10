import { Component, OnInit } from '@angular/core';
import { BandaItemList } from '../../models/banda-item-list';
import { BandaService } from '../../services/banda.service';

interface DropdownOption {
  display: string;
  value: string;
}

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-bandas-list-page',
  templateUrl: './bandas-list-page.component.html',
  styleUrl: './bandas-list-page.component.css',
})
export class BandasListPageComponent implements OnInit {
  options: DropdownOption[] = [
    { display: 'Todas', value: '' },
    { display: 'Bandas de música', value: 'BANDA_MUSICA' },
    { display: 'Bandas de cornetas y tambores', value: 'BANDA_CCTT' },
    { display: 'Agrupaciones musicales', value: 'AGRUPACION_MUSICAL' },
    { display: 'Musica de capilla', value: 'MUSICA_CAPILLA' },
    { display: 'Coro', value: 'CORO' },
  ];

  selectedOption: DropdownOption = this.options[0];
  bandaItems: BandaItemList[] = [];
  page = 1;
  pageSize = 11;
  collectionSize = 0;

  constructor(private bandaService: BandaService) {}
  ngOnInit(): void {
    this.fetchBandas();
  }

  onOptionChange(option: DropdownOption) {
    this.selectedOption = option;
    this.page = 1;
    this.fetchBandas();
  }

  fetchBandas() {
    this.bandaService
      .getBandasList(this.selectedOption.value)
      .subscribe((items) => {
        this.bandaItems = items;
        this.collectionSize = items.length;
      });
  }
  get paginatedItems(): BandaItemList[] {
    return this.bandaItems.slice(
      (this.page - 1) * this.pageSize,
      this.page * this.pageSize
    );
  }

  selectPage(page: string) {
    this.page = parseInt(page, 10) || 1;
  }

  formatInput(input: Event) {
    const target = input.target as HTMLInputElement;
    target.value = target.value.replace(FILTER_PAG_REGEX, '');
  }

  deleteBanda(id: string) {
    this.bandaService.deleteBanda(id).subscribe(() => {
      this.fetchBandas();
    });
  }
}
