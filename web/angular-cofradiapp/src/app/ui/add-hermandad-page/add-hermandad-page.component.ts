import { Component } from '@angular/core';
import { PostHermandadDTO } from '../../models/post-hermandad-dto';
import { HermandadService } from '../../services/hermandad.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-hermandad-page',
  templateUrl: './add-hermandad-page.component.html',
  styleUrl: './add-hermandad-page.component.css',
})
export class AddHermandadPageComponent {
  hermandadForm: FormGroup;
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
    private router: Router
  ) {
    this.hermandadForm = this.fb.group({
      dia: ['', Validators.required],
      nombre: ['', Validators.required],
      nombreCompleto: ['', Validators.required],
      deInteres: ['', Validators.required],
      annoFundacion: ['', [Validators.required, Validators.min(0)]],
      numHermanos: ['', [Validators.required, Validators.min(0)]],
      numNazarenos: ['', [Validators.required, Validators.min(0)]],
      tiempoPaso: ['', [Validators.required, Validators.min(0)]],
      sede: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.hermandadForm.valid) {
      const newHermandad: PostHermandadDTO = this.hermandadForm.value;
      this.hermandadService.addHermandad(newHermandad).subscribe({
        next: (response) => {
          console.log('Hermandad añadida exitosamente:', response);
          // Redirigir a la lista de hermandades
          this.router.navigate(['/admin/hermandades']);
        },
        error: (error) => {
          console.error('Error al añadir la hermandad:', error);
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
    Object.values(this.hermandadForm.controls).forEach((control) => {
      control.markAsTouched();
    });
  }
}
