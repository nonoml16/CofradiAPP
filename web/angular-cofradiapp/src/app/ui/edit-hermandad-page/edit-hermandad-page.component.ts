import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HermandadService } from '../../services/hermandad.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostHermandadDTO } from '../../models/post-hermandad-dto';
import { Card, HermandadDetails, Paso } from '../../models/hermandad-details';
import { CardService } from '../../services/card.service';

@Component({
  selector: 'app-edit-hermandad-page',
  templateUrl: './edit-hermandad-page.component.html',
  styleUrl: './edit-hermandad-page.component.css',
})
export class EditHermandadPageComponent implements OnInit {
  hermandadForm!: FormGroup;
  hermandadId: string | null = null;
  hermandadNombre: string | null = null;
  pasos: Paso[] = [];
  cards: Card[] = [];
  options = [
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

  constructor(
    private fb: FormBuilder,
    private hermandadService: HermandadService,
    private cardService: CardService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.hermandadForm = this.fb.group({
      nombre: ['', Validators.required],
      nombreCompleto: ['', Validators.required],
      deInteres: ['', Validators.required],
      dia: ['', Validators.required],
      annoFundacion: [0, Validators.required],
      numHermanos: [0, Validators.required],
      numNazarenos: [0, Validators.required],
      tiempoPaso: [0, Validators.required],
      sede: ['', Validators.required],
    });

    this.hermandadId = this.route.snapshot.paramMap.get('id');
    if (this.hermandadId) {
      this.hermandadService
        .getHermandad(this.hermandadId)
        .subscribe((hermandad: HermandadDetails) => {
          this.hermandadNombre = hermandad.nombre;
          this.pasos = hermandad.pasos;
          this.cards = hermandad.cards;
          this.hermandadForm.patchValue({
            nombre: hermandad.nombre,
            nombreCompleto: hermandad.nombreCompleto,
            deInteres: hermandad.deInteres,
            annoFundacion: hermandad.annoFundacion,
            numHermanos: hermandad.numHermanos,
            numNazarenos: hermandad.numNazarenos,
            tiempoPaso: hermandad.tiempoPaso,
            sede: hermandad.sede,
          });

          this.hermandadService
            .getDiaHermandad(this.hermandadId!)
            .subscribe((dia: string) => {
              this.hermandadForm.patchValue({ dia: dia });
            });
        });
    }
  }

  onSubmit(): void {
    if (this.hermandadForm.valid && this.hermandadId) {
      const updatedHermandad: PostHermandadDTO = this.hermandadForm.value;
      this.hermandadService
        .updateHermandad(this.hermandadId, updatedHermandad)
        .subscribe({
          next: (response) => {
            console.log('Hermandad actualizada exitosamente:', response);
            this.router.navigate(['/admin/hermandades']);
          },
          error: (error) => {
            console.error('Error al actualizar la hermandad:', error);
          },
        });
    }
  }

  deletePaso(id: string): void {
    this.hermandadService.deletePaso(id).subscribe({
      next: () => {
        this.pasos = this.pasos.filter((paso) => paso.id !== id);
      },
      error: (error) => {
        console.error('Error al eliminar el paso:', error);
      },
    });
  }

  addPaso() {
    this.router.navigate([
      `/admin/hermandades/editar/${this.hermandadId}/nuevo-paso`,
    ]);
  }

  deleteCard(id: number): void {
    this.cardService.deleteCard(id).subscribe({
      next: () => {
        this.cards = this.cards.filter((card) => card.id !== id);
      },
      error: (error) => {
        console.error('Error al eliminar la card:', error);
      },
    });
  }
}
