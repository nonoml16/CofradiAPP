import { Component, OnInit } from '@angular/core';
import { HermandadItemList } from '../../models/hermandad-item-list';
import { HermandadService } from '../../services/hermandad.service';
import { Router } from '@angular/router';

interface DropdownOption {
  display: string;
  value: string;
}

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-hermandad-list-page',
  templateUrl: './hermandad-list-page.component.html',
  styleUrl: './hermandad-list-page.component.css',
})
export class HermandadListPageComponent implements OnInit {
  options: DropdownOption[] = [
    { display: 'Todas', value: '' },
    { display: 'Viernes de Dolores', value: 'VIERNES_DOLORES' },
    { display: 'Sábado de Pasión', value: 'SABADO_PASION' },
    { display: 'Domingo de Ramos', value: 'DOMINGO_DE_RAMOS' },
    { display: 'Lunes Santo', value: 'LUNES_SANTO' },
    { display: 'Martes Santo', value: 'MARTES_SANTO' },
    { display: 'Miércoles Santo', value: 'MIERCOLES_SANTO' },
    { display: 'Jueves Santo', value: 'JUEVES_SANTO' },
    { display: 'Viernes Santo (Madrugá)', value: 'VIERNES_SANTO_MADRUGA' },
    { display: 'Viernes Santo', value: 'VIERNES_SANTO' },
    { display: 'Sábado Santo', value: 'SABADO_SANTO' },
    { display: 'Domingo de Resurrección', value: 'DOMINGO_RESURRECCION' },
  ];

  selectedOption: DropdownOption = this.options[0];
  hermandadItems: HermandadItemList[] = [];
  page = 1;
  pageSize = 11;
  collectionSize = 0;

  constructor(
    private hermandadService: HermandadService,
    private router: Router
  ) {}

  ngOnInit() {
    this.fetchHermandades();
  }

  onOptionChange(option: DropdownOption) {
    this.selectedOption = option;
    this.page = 1;
    this.fetchHermandades();
  }

  fetchHermandades() {
    this.hermandadService
      .getHermandadList(this.selectedOption.value)
      .subscribe((items) => {
        this.hermandadItems = items;
        this.collectionSize = items.length;
      });
  }

  get paginatedItems(): HermandadItemList[] {
    return this.hermandadItems.slice(
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

  deleteHermandad(id: string) {
    this.hermandadService.deleteHermandad(id).subscribe(() => {
      this.fetchHermandades();
    });
  }

  goToAddHermandad() {
    this.router.navigate(['/admin/hermandades/nueva']);
  }
}
