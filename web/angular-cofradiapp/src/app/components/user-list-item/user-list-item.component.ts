import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserItemList } from '../../models/user-item-list';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImagenService } from '../../services/imagen.service';

@Component({
  selector: 'app-user-list-item',
  templateUrl: './user-list-item.component.html',
  styleUrl: './user-list-item.component.css',
})
export class UserListItemComponent implements OnInit {
  @Input() user!: UserItemList;
  @Output() delete = new EventEmitter<string>();

  imageUrl!: SafeUrl;

  constructor(
    private imagenService: ImagenService,
    private sanitizer: DomSanitizer
  ) {}
  ngOnInit(): void {
    this.imagenService.getImage(this.user.fotoPerfil).subscribe(
      (response) => {
        const objectURL = URL.createObjectURL(response);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      (error) => {
        console.error('Error al cargar la imagen:', error);
      }
    );
  }

  getFormattedNumCards(): string {
    return this.user.numCards === 1 ? '1 card' : `${this.user.numCards} cards`;
  }

  onDelete() {
    this.delete.emit(this.user.id);
  }
}
