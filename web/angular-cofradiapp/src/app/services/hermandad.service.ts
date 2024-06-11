import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { HermandadItemList } from '../models/hermandad-item-list';
import { PostHermandadDTO } from '../models/post-hermandad-dto';

@Injectable({
  providedIn: 'root',
})
export class HermandadService {
  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';
  router: any;

  constructor(private http: HttpClient) {}

  getHermandadList(dia: string): Observable<HermandadItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<HermandadItemList[]>(`${this.apiBaseUrl}/hermandades/web/${dia}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar las hermandades';
          return throwError(errorMessage);
        })
      );
  }

  addHermandad(hermandad: PostHermandadDTO): Observable<PostHermandadDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .post<PostHermandadDTO>(
        `${this.apiBaseUrl}/hermandades/hermandad/nueva`,
        {
          nombre: hermandad.nombre,
          nombreCompleto: hermandad.nombreCompleto,
          deInteres: hermandad.deInteres,
          dia: hermandad.dia,
          annoFundacion: hermandad.annoFundacion,
          numHermanos: hermandad.numHermanos,
          numNazarenos: hermandad.numNazarenos,
          tiempoPaso: hermandad.tiempoPaso,
          sede: hermandad.sede,
        },
        { headers }
      )
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }
  deleteHermandad(id: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/hermandades/hermandad/${id}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la hermandad';
          return throwError(errorMessage);
        })
      );
  }
}
