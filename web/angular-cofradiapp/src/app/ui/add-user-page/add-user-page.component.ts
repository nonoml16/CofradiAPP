import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileService } from '../../services/file.service';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostUserDTO } from '../../models/post-user-dto';
import { HermandadItemList } from '../../models/hermandad-item-list';
import { HermandadService } from '../../services/hermandad.service';

@Component({
  selector: 'app-add-user-page',
  templateUrl: './add-user-page.component.html',
  styleUrl: './add-user-page.component.css',
})
export class AddUserPageComponent implements OnInit {
  userForm: FormGroup;
  selectedFile!: File;
  availableHermandades: HermandadItemList[] = []; // Lista de acompañamientos disponibles
  selectedHermandad: string | null = null;

  constructor(
    private fb: FormBuilder,
    private fileService: FileService,
    private userService: UserService,
    private route: ActivatedRoute,
    private hermandadService: HermandadService,
    private router: Router
  ) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      hermandadId: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.loadAvailableHermandades();
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
        const card: PostUserDTO = {
          username: this.userForm.get('username')!.value,
          password: this.userForm.get('password')!.value,
          email: this.userForm.get('email')!.value,
          nombre: this.userForm.get('nombre')!.value,
          apellidos: this.userForm.get('apellidos')!.value,
          avatar: response.uri,
          hermandadId: this.userForm.get('hermandadId')!.value,
        };

        this.userService.addUser(card).subscribe((cardResponse) => {
          this.router.navigate(['/admin/users']);
        });
      });
    } else {
      console.error('No file selected.');
    }
  }

  loadAvailableHermandades(): void {
    this.hermandadService.getAllHermandadList().subscribe((data) => {
      this.availableHermandades = data;
    });
  }
}
