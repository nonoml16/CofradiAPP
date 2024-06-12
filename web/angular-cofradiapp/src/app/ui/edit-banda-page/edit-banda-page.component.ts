import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HermandadService } from '../../services/hermandad.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BandaService } from '../../services/banda.service';
import { BandaDetails, Hermandad } from '../../models/banda-details';
import { PostBandaDTO } from '../../models/post-banda-dto';

@Component({
  selector: 'app-edit-banda-page',
  templateUrl: './edit-banda-page.component.html',
  styleUrl: './edit-banda-page.component.css',
})
export class EditBandaPageComponent implements OnInit {
  bandaForm!: FormGroup;
  bandaId: string | null = null;
  bandaNombre: string | null = null;
  hermandades: Hermandad[] = [];
  options = [
    { display: 'Banda de Música', value: 'BANDA_MUSICA' },
    { display: 'Banda de Cornetas y Tambores', value: 'BANDA_CCTT' },
    { display: 'Agrupación Musical', value: 'AGRUPACION_MUSICAL' },
    { display: 'Musica de Capilla', value: 'MUSICA_CAPILLA' },
    { display: 'Coro', value: 'CORO' },
  ];

  constructor(
    private fb: FormBuilder,
    private bandaService: BandaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.bandaForm = this.fb.group({
      nombre: ['', Validators.required],
      localidad: ['', Validators.required],
      tipo: ['', Validators.required],
      annoFundacion: ['', Validators.required],
    });

    this.bandaId = this.route.snapshot.paramMap.get('id');
    if (this.bandaId) {
      this.bandaService
        .getBanda(this.bandaId)
        .subscribe((banda: BandaDetails) => {
          this.bandaNombre = banda.nombre;
          this.hermandades = banda.hermandades;
          this.bandaForm.patchValue({
            nombre: banda.nombre,
            localidad: banda.localidad,
            annoFundacion: banda.anno,
          });

          this.bandaService
            .getTipoBanda(this.bandaId!)
            .subscribe((tipo: string) => {
              this.bandaForm.patchValue({ tipo: tipo });
            });
        });
    }
  }

  onSubmit(): void {
    if (this.bandaForm.valid && this.bandaId) {
      const updatedHermandad: PostBandaDTO = this.bandaForm.value;
      this.bandaService.updateBanda(this.bandaId, updatedHermandad).subscribe({
        next: (response) => {
          console.log('Banda actualizada exitosamente:', response);
          this.router.navigate(['/admin/bandas']);
        },
        error: (error) => {
          console.error('Error al actualizar la banda:', error);
        },
      });
    }
  }

  deleteHermandad(id: string): void {
    this.bandaService.deleteMusicaHermandad(this.bandaId!, id).subscribe({
      next: () => {
        this.hermandades = this.hermandades.filter(
          (hermandad) => hermandad.id !== id
        );
      },
      error: (error) => {
        console.error('Error al eliminar la hermandad:', error);
      },
    });
  }
}
