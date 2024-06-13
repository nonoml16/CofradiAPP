import { Component } from '@angular/core';
import { FileService } from '../../services/file.service';
import { CardService } from '../../services/card.service';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { PostCardDTO } from '../../models/post-card-dto';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-card-page',
  templateUrl: './add-card-page.component.html',
  styleUrl: './add-card-page.component.css',
})
export class AddCardPageComponent {
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

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files!.length > 0) {
      this.selectedFile = input.files![0];
    }
  }

  onSubmit() {
    if (this.selectedFile) {
      this.fileService.uploadFile(this.selectedFile).subscribe((response) => {
        const card: PostCardDTO = {
          urlImagen: response.uri,
          titulo: this.cardForm.get('title')!.value,
          descripcion: this.cardForm.get('description')!.value,
          nombreFotografo: this.cardForm.get('nombreFotografo')!.value,
          tipo: this.cardForm.get('tipo')!.value,
        };

        const hermandadId = this.route.snapshot.paramMap.get('id');
        this.cardService
          .addCard(card, hermandadId!)
          .subscribe((cardResponse) => {
            this.router.navigate(['/admin/cards']);
          });
      });
    } else {
      console.error('No file selected.');
    }
  }
}
