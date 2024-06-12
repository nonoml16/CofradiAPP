import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HermandadService } from '../../services/hermandad.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Acompannamiento, PasoDetails } from '../../models/paso-details';
import { PutPasoDTO } from '../../models/put-paso-dto';
import { BandaItemList } from '../../models/banda-item-list';
import { BandaService } from '../../services/banda.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-edit-paso-page',
  templateUrl: './edit-paso-page.component.html',
  styleUrl: './edit-paso-page.component.css',
})
export class EditPasoPageComponent implements OnInit {
  pasoForm!: FormGroup;
  pasoId: string | null = null;
  pasoNombre: string | null = null;
  musicas: Acompannamiento[] = [];
  availableMusicas: BandaItemList[] = []; // Lista de acompañamientos disponibles
  selectedMusica: string | null = null;

  @ViewChild('addMusicaModal') addMusicaModal: any;

  constructor(
    private fb: FormBuilder,
    private hermandadService: HermandadService,
    private bandaService: BandaService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}
  ngOnInit(): void {
    this.pasoForm = this.fb.group({
      capataz: ['', Validators.required],
      numCostaleros: ['', Validators.required],
    });

    this.pasoId = this.route.snapshot.paramMap.get('id');
    if (this.pasoId) {
      this.hermandadService
        .getPaso(this.pasoId)
        .subscribe((paso: PasoDetails) => {
          this.pasoNombre = paso.imagen;
          this.musicas = paso.acompannamiento;
          this.pasoForm.patchValue({
            capataz: paso.capataz,
            numCostaleros: paso.numCostaleros,
          });
        });
    }

    this.loadAvailableMusicas();
  }

  loadAvailableMusicas(): void {
    this.bandaService.getAllBandasList().subscribe((data) => {
      this.availableMusicas = data;
    });
  }

  openAddMusicaModal(): void {
    this.modalService.open(this.addMusicaModal);
  }

  addMusica(): void {
    if (this.pasoId && this.selectedMusica) {
      this.hermandadService
        .addMusicaToPaso(this.pasoId, this.selectedMusica)
        .subscribe(() => {
          this.hermandadService
            .getPaso(this.pasoId!)
            .subscribe((paso: PasoDetails) => {
              this.musicas = paso.acompannamiento;
            });
        });
    }
    this.modalService.dismissAll();
  }

  onSubmit(): void {
    if (this.pasoForm.valid && this.pasoId) {
      const updatedPaso: PutPasoDTO = this.pasoForm.value;
      this.hermandadService.updatePaso(this.pasoId, updatedPaso).subscribe({
        next: (response) => {
          console.log('Paso actualizado exitosamente:', response);
          this.router.navigate(['/admin/hermandades']);
        },
        error: (error) => {
          console.error('Error al actualizar el paso:', error);
        },
      });
    }
  }

  deleteMusica(id: string): void {
    this.hermandadService.deletePasoMusica(this.pasoId!, id).subscribe({
      next: () => {
        this.musicas = this.musicas.filter((musica) => musica.id !== id);
      },
      error: (error) => {
        console.error('Error al eliminar la musica:', error);
      },
    });
  }
}
