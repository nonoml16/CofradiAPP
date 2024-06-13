import { Component, OnInit } from '@angular/core';
import { PostCardDTO } from '../../models/post-card-dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileService } from '../../services/file.service';
import { CardService } from '../../services/card.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-card-page',
  templateUrl: './edit-card-page.component.html',
  styleUrl: './edit-card-page.component.css',
})
export class EditCardPageComponent implements OnInit {
  cardForm: FormGroup;
  selectedFile!: File;
  options = [
    { display: 'Cruz de guía', value: 'CRUZ_DE_GUIA' },
    { display: 'Insignia', value: 'INSIGNIA' },
    { display: 'Paso', value: 'PASO' },
    { display: 'Palio', value: 'PALIO' },
    { display: 'Cristo', value: 'CRISTO' },
    { display: 'Virgen', value: 'VIRGEN' },
    { display: 'Escudo', value: 'ESCUDO' },
    { display: 'Flash', value: 'FLASH' },
  ];
  cardId!: number;
  hermandadId: string = 'hermandad-id-value'; // Reemplaza esto con el ID correcto

  constructor(
    private fb: FormBuilder,
    private fileService: FileService,
    private cardService: CardService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.cardForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      nombreFotografo: ['', Validators.required],
      tipo: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cardId = +id; // Convertimos a número
      this.cardService.getCard(this.cardId).subscribe((card) => {
        this.cardForm.patchValue({
          title: card.titulo,
          description: card.descripcion,
          nombreFotografo: card.nombreFotografo,
          tipo: card.tipoCard,
        });
      });
    } else {
      // Manejar el caso en que no se encuentra el ID, por ejemplo, redirigir o mostrar un mensaje de error
      console.error('No se encontró el ID de la tarjeta');
    }
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files!.length > 0) {
      this.selectedFile = input.files![0];
    }
  }

  onSubmit() {
    if (this.selectedFile) {
      this.fileService.uploadFile(this.selectedFile).subscribe((response) => {
        const updatedCard: PostCardDTO = {
          urlImagen: response.uri,
          titulo: this.cardForm.get('title')!.value,
          descripcion: this.cardForm.get('description')!.value,
          nombreFotografo: this.cardForm.get('nombreFotografo')!.value,
          tipo: this.cardForm.get('tipo')!.value,
        };

        this.cardService
          .updateCard(this.cardId, updatedCard)
          .subscribe((cardResponse) => {
            console.log(cardResponse);
            this.router.navigate(['/admin/cards']); // Navegar a la lista de cards después de la actualización
          });
      });
    } else {
      const updatedCard: PostCardDTO = {
        urlImagen: '', // Mantén la URL de la imagen actual
        titulo: this.cardForm.get('title')!.value,
        descripcion: this.cardForm.get('description')!.value,
        nombreFotografo: this.cardForm.get('nombreFotografo')!.value,
        tipo: this.cardForm.get('tipo')!.value,
      };

      this.cardService
        .updateCard(this.cardId, updatedCard)
        .subscribe((cardResponse) => {
          console.log(cardResponse);
          this.router.navigate(['/admin/cards']); // Navegar a la lista de cards después de la actualización
        });
    }
  }
}
