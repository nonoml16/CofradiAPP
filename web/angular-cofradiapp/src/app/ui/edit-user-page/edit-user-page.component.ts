import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileService } from '../../services/file.service';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PutUserDTO } from '../../models/put-user-dto';
import { HermandadService } from '../../services/hermandad.service';
import { HermandadesFavorita } from '../../models/user-details';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Card } from '../../models/hermandad-details';
import { CardService } from '../../services/card.service';
import { CardItemList } from '../../models/card-item-list';

@Component({
  selector: 'app-edit-user-page',
  templateUrl: './edit-user-page.component.html',
  styleUrl: './edit-user-page.component.css',
})
export class EditUserPageComponent implements OnInit {
  userForm: FormGroup;
  selectedFile!: File;
  userId!: string; // Variable para almacenar el ID del usuario que se está editando
  existingAvatarUrl: string | null = null; // Variable para almacenar la URL del avatar existente

  hermandades: HermandadesFavorita[] = [];
  hermandadesFavoritas: HermandadesFavorita[] = [];
  cards: Card[] = [];
  selectedHermandad: string | null = null;
  allCards: CardItemList[] = [];
  selectedCard: number | null = null;

  @ViewChild('addHermandadModal') addHermandadModal: any;
  @ViewChild('addCardModal') addCardModal: any;

  constructor(
    private fb: FormBuilder,
    private fileService: FileService,
    private userService: UserService,
    private hermandadService: HermandadService,
    private cardService: CardService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      hermandadId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params['id']; // Obtener el ID del usuario desde la URL

    // Lógica para cargar los datos del usuario a editar (por ejemplo, mediante un servicio)
    this.loadUserData();
    this.loadHermandades();
    this.loadCards();
  }

  loadUserData() {
    // Ejemplo de carga de datos del usuario a editar (debes implementar según tu lógica)
    // Supongamos que obtienes los datos del usuario y los cargas en el formulario
    this.userService.getUser(this.userId).subscribe(
      (userData) => {
        this.existingAvatarUrl = userData.avatar; // Guarda la URL del avatar existente
        this.hermandadesFavoritas = userData.hermandadesFavoritas;
        this.cards = userData.cards;
        this.userForm.patchValue({
          username: userData.username,
          email: userData.email,
          nombre: userData.nombre,
          apellidos: userData.apellidos,
          hermandadId: userData.hermandadId,
        });
      },
      (error) => {
        console.error('Error al cargar datos del usuario:', error);
        // Manejar el error según sea necesario
      }
    );
  }

  loadHermandades() {
    this.hermandadService.getAllHermandadList().subscribe(
      (data) => {
        this.hermandades = data;
      },
      (error) => {
        console.error('Error al cargar hermandades:', error);
      }
    );
  }

  loadCards() {
    this.cardService.getCardsList('').subscribe(
      (data) => {
        this.allCards = data;
      },
      (error) => {
        console.error('Error al cargar cards:', error);
      }
    );
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files!.length > 0) {
      this.selectedFile = input.files![0];
    }
  }

  onSubmit() {
    if (this.selectedFile) {
      this.fileService.uploadFile(this.selectedFile).subscribe(
        (response) => {
          const user: PutUserDTO = {
            username: this.userForm.get('username')!.value,
            email: this.userForm.get('email')!.value,
            nombre: this.userForm.get('nombre')!.value,
            apellidos: this.userForm.get('apellidos')!.value,
            avatar: response.uri,
            idHermandad: this.userForm.get('hermandadId')!.value,
          };

          console.log(user.idHermandad);
          console.log(this.userId);
          this.userService.updateUser(this.userId, user).subscribe(
            () => {
              this.router.navigate(['/admin/users']);
            },
            (error) => {
              console.error('Error al actualizar usuario:', error);
              // Manejar el error al actualizar usuario, si es necesario
            }
          );
        },
        (error) => {
          console.error('Error al subir archivo:', error);
          // Mostrar el mensaje de error al usuario según el tipo de error
        }
      );
    } else {
      // Lógica para actualizar el usuario sin cambiar la foto de perfil si no se selecciona ninguna nueva
      const userWithoutAvatar: PutUserDTO = {
        username: this.userForm.get('username')!.value,
        email: this.userForm.get('email')!.value,
        nombre: this.userForm.get('nombre')!.value,
        apellidos: this.userForm.get('apellidos')!.value,
        avatar: this.existingAvatarUrl!, // Usa la URL del avatar existente
        idHermandad: this.userForm.get('hermandadId')!.value,
      };
      console.log(userWithoutAvatar);
      console.log(this.userId);
      this.userService.updateUser(this.userId, userWithoutAvatar).subscribe(
        () => {
          this.router.navigate(['/admin/users']);
        },
        (error) => {
          console.error('Error al actualizar usuario:', error);
          // Manejar el error al actualizar usuario, si es necesario
        }
      );
    }
  }

  deleteHermandadFav(id: string): void {
    this.userService.deleteUserHermandadFav(this.userId!, id).subscribe({
      next: () => {
        this.hermandadesFavoritas = this.hermandadesFavoritas.filter(
          (hermandad) => hermandad.id !== id
        );
      },
      error: (error) => {
        console.error('Error al eliminar la hermandad:', error);
      },
    });
  }

  addHermandad(): void {
    if (this.selectedHermandad) {
      this.userService
        .addUserHermandadFav(this.userId, this.selectedHermandad)
        .subscribe(
          () => {
            window.location.reload();
          },
          (error) => {
            console.error('Error al actualizar usuario:', error);
            // Manejar el error al actualizar usuario, si es necesario
          }
        );
    }
    this.modalService.dismissAll();
  }

  openAddHermandadModal(): void {
    this.modalService.open(this.addHermandadModal);
  }

  deleteCard(id: number): void {
    this.userService.deleteUserCard(this.userId, id).subscribe({
      next: () => {
        this.cards = this.cards.filter((card) => card.id !== id);
      },
      error: (error) => {
        console.error('Error al eliminar la card:', error);
      },
    });
  }

  addCard(): void {
    if (this.selectedCard) {
      this.userService.addUserCard(this.userId, this.selectedCard!).subscribe(
        () => {
          window.location.reload();
        },
        (error) => {
          console.error('Error al actualizar usuario:', error);
          // Manejar el error al actualizar usuario, si es necesario
        }
      );
    }
    this.modalService.dismissAll();
  }

  openAddCardModal(): void {
    this.modalService.open(this.addCardModal);
  }
}
