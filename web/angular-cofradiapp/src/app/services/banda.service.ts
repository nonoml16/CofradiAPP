import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { BandaItemList } from '../models/banda-item-list';
import { PostBandaDTO } from '../models/post-banda-dto';
import { BandaDetails } from '../models/banda-details';

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

  getBanda(id: string): Observable<BandaDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<BandaDetails>(`${this.apiBaseUrl}/musica/${id}`, {
      headers,
    });
  }

  getTipoBanda(id: string): Observable<string> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<string>(`${this.apiBaseUrl}/musica/${id}/tipo`, {
      headers,
    });
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

  addBanda(banda: PostBandaDTO): Observable<PostBandaDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .post<PostBandaDTO>(
        `${this.apiBaseUrl}/musica/nueva`,
        {
          nombre: banda.nombre,
          localidad: banda.localidad,
          tipo: banda.tipo,
          annoFundacion: banda.annoFundacion,
        },
        { headers }
      )
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  updateBanda(id: string, banda: PostBandaDTO): Observable<PostBandaDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .put<PostBandaDTO>(`${this.apiBaseUrl}/musica/${id}`, banda, {
        headers,
      })
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  deleteMusicaHermandad(
    idMusica: string,
    idHermandad: string
  ): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(
        `${this.apiBaseUrl}/musica/${idMusica}/hermandad/${idHermandad}`,
        {
          headers,
        }
      )
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la musica';
          return throwError(errorMessage);
        })
      );
  }
}
