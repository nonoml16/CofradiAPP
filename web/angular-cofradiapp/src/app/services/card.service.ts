import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { CardItemList } from '../models/card-item-list';

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
}
