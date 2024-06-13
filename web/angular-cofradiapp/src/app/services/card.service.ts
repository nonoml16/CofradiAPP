import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { CardItemList } from '../models/card-item-list';
import { PostCardDTO } from '../models/post-card-dto';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) {}

  getCardsList(tipo: string): Observable<CardItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<CardItemList[]>(`${this.apiBaseUrl}/cards/web/${tipo}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar las cards';
          return throwError(errorMessage);
        })
      );
  }

  deleteCard(id: number): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/cards/card/${id}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la card';
          return throwError(errorMessage);
        })
      );
  }

  addCard(card: PostCardDTO, hermandadId: string): Observable<PostCardDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .post<PostCardDTO>(
        `${this.apiBaseUrl}/cards/nueva/${hermandadId}`,
        {
          urlImagen: card.urlImagen,
          titulo: card.titulo,
          descripcion: card.descripcion,
          nombreFotografo: card.nombreFotografo,
          tipo: card.tipo,
        },
        { headers }
      )
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }
}
