import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HermandadService } from '../../services/hermandad.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostPasoDTO } from '../../models/post-paso-dto';

@Component({
  selector: 'app-add-paso-page',
  templateUrl: './add-paso-page.component.html',
  styleUrl: './add-paso-page.component.css',
})
export class AddPasoPageComponent implements OnInit {
  pasoForm!: FormGroup;
  hermandadId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private hermandadService: HermandadService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.pasoForm = this.fb.group({
      imagen: ['', Validators.required],
      capataz: ['', Validators.required],
      numCostaleros: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.hermandadId = this.route.snapshot.paramMap.get('id');
  }

  onSubmit() {
    if (this.pasoForm.valid) {
      const newPaso: PostPasoDTO = this.pasoForm.value;
      this.hermandadService.addPaso(newPaso, this.hermandadId!).subscribe({
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
    Object.values(this.pasoForm.controls).forEach((control) => {
      control.markAsTouched();
    });
  }
}
