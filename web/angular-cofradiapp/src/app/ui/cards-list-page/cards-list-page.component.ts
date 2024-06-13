import { Component, OnInit, ViewChild } from '@angular/core';
import { CardItemList } from '../../models/card-item-list';
import { CardService } from '../../services/card.service';
import { Router } from '@angular/router';
import { HermandadItemList } from '../../models/hermandad-item-list';
import { HermandadService } from '../../services/hermandad.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

interface DropdownOption {
  display: string;
  value: string;
}

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-cards-list-page',
  templateUrl: './cards-list-page.component.html',
  styleUrl: './cards-list-page.component.css',
})
export class CardsListPageComponent implements OnInit {
  options: DropdownOption[] = [
    { display: 'Todas', value: '' },
    { display: 'Cruz de guía', value: 'CRUZ_DE_GUIA' },
    { display: 'Insignia', value: 'INSIGNIA' },
    { display: 'Paso', value: 'PASO' },
    { display: 'Palio', value: 'PALIO' },
    { display: 'Cristo', value: 'CRISTO' },
    { display: 'Virgen', value: 'VIRGEN' },
    { display: 'Escudo', value: 'ESCUDO' },
    { display: 'Flash', value: 'FLASH' },
  ];

  selectedOption: DropdownOption = this.options[0];
  cardItems: CardItemList[] = [];
  page = 1;
  pageSize = 11;
  collectionSize = 0;
  availableHermandades: HermandadItemList[] = []; // Lista de acompañamientos disponibles
  selectedHermandad: string | null = null;

  @ViewChild('addHermandadModal') addHermandadModal: any;

  constructor(
    private cardService: CardService,
    private hermandadService: HermandadService,
    private router: Router,
    private modalService: NgbModal
  ) {}
  ngOnInit(): void {
    this.fetchCards();
    this.loadAvailableHermandades();
  }

  onOptionChange(option: DropdownOption) {
    this.selectedOption = option;
    this.page = 1;
    this.fetchCards();
  }

  fetchCards() {
    this.cardService
      .getCardsList(this.selectedOption.value)
      .subscribe((items) => {
        this.cardItems = items;
        this.collectionSize = items.length;
      });
  }
  get paginatedItems(): CardItemList[] {
    return this.cardItems.slice(
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

  deleteCard(id: number) {
    this.cardService.deleteCard(id).subscribe(() => {
      this.fetchCards();
    });
  }

  loadAvailableHermandades(): void {
    this.hermandadService.getAllHermandadList().subscribe((data) => {
      this.availableHermandades = data;
    });
  }

  openAddHermandadModal(): void {
    this.modalService.open(this.addHermandadModal);
  }

  addHermandad(): void {
    if (this.selectedHermandad) {
      this.router.navigate(['/admin/cards/nueva', this.selectedHermandad]);
    }
    this.modalService.dismissAll();
  }
}
