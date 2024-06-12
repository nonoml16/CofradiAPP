import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { BandaItemList } from '../models/banda-item-list';

@Injectable({
  providedIn: 'root',
})
export class BandaService {
  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) {}

  getBandasList(tipo: string): Observable<BandaItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<BandaItemList[]>(`${this.apiBaseUrl}/musica/web/${tipo}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar las bandas';
          return throwError(errorMessage);
        })
      );
  }

  getAllBandasList(): Observable<BandaItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<BandaItemList[]>(`${this.apiBaseUrl}/musica/web/`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar las bandas';
          return throwError(errorMessage);
        })
      );
  }

  deleteBanda(id: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/musica/${id}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la banda';
          return throwError(errorMessage);
        })
      );
  }
}
