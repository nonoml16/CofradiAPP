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
          let errorMessage = 'Error al cargar el usuario';
          return throwError(errorMessage);
        })
      );
  }
}
