import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BandaService } from '../../services/banda.service';
import { Router } from '@angular/router';
import { PostBandaDTO } from '../../models/post-banda-dto';

@Component({
  selector: 'app-add-banda-page',
  templateUrl: './add-banda-page.component.html',
  styleUrl: './add-banda-page.component.css',
})
export class AddBandaPageComponent {
  bandaForm: FormGroup;
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
    private router: Router
  ) {
    this.bandaForm = this.fb.group({
      nombre: ['', Validators.required],
      localidad: ['', Validators.required],
      tipo: ['', Validators.required],
      annoFundacion: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.bandaForm.valid) {
      const newBanda: PostBandaDTO = this.bandaForm.value;
      this.bandaService.addBanda(newBanda).subscribe({
        next: (response) => {
          console.log('Hermandad añadida exitosamente:', response);
          // Redirigir a la lista de hermandades
          this.router.navigate(['/admin/bandas']);
        },
        error: (error) => {
          console.error('Error al añadir la banda:', error);
          // Maneja el error aquí, por ejemplo, mostrando un mensaje al usuario
        },
      });
    } else {
      // Marcar todos los campos como tocados para mostrar los mensajes de error
      this.markAllAsTouched();
    }
  }

  markAllAsTouched() {
    // Marcar todos los campos como tocados para mostrar los mensajes de error
    Object.values(this.bandaForm.controls).forEach((control) => {
      control.markAsTouched();
    });
  }
}
