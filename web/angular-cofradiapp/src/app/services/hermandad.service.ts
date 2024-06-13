import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { HermandadItemList } from '../models/hermandad-item-list';
import { PostHermandadDTO } from '../models/post-hermandad-dto';
import { HermandadDetails } from '../models/hermandad-details';
import { PasoDetails } from '../models/paso-details';
import { PutPasoDTO } from '../models/put-paso-dto';
import { PostPasoDTO } from '../models/post-paso-dto';

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

  getAllHermandadList(): Observable<HermandadItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<HermandadItemList[]>(`${this.apiBaseUrl}/hermandades/web/`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar las hermandades';
          return throwError(errorMessage);
        })
      );
  }

  getHermandad(id: string): Observable<HermandadDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<HermandadDetails>(
      `${this.apiBaseUrl}/hermandades/hermandad/${id}`,
      { headers }
    );
  }

  getDiaHermandad(id: string): Observable<string> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<string>(
      `${this.apiBaseUrl}/hermandades/hermandad/${id}/dia`,
      { headers }
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

  updateHermandad(
    id: string,
    hermandad: PostHermandadDTO
  ): Observable<PostHermandadDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .put<PostHermandadDTO>(
        `${this.apiBaseUrl}/hermandades/hermandad/editar/${id}`,
        hermandad,
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

  getPaso(id: string): Observable<PasoDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<PasoDetails>(`${this.apiBaseUrl}/pasos/paso/${id}`, {
      headers,
    });
  }

  addPaso(paso: PostPasoDTO, hermandadId: string): Observable<PostPasoDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .post<PostPasoDTO>(
        `${this.apiBaseUrl}/pasos/paso/nuevo/${hermandadId}`,
        {
          imagen: paso.imagen,
          capataz: paso.capataz,
          numCostaleros: paso.numCostaleros,
        },
        { headers }
      )
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  updatePaso(id: string, paso: PutPasoDTO): Observable<PutPasoDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .put<PutPasoDTO>(`${this.apiBaseUrl}/pasos/paso/editar/${id}`, paso, {
        headers,
      })
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  deletePaso(id: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/pasos/paso/${id}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar el paso';
          return throwError(errorMessage);
        })
      );
  }

  addMusicaToPaso(pasoId: string, musicaId: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .post(
        `${this.apiBaseUrl}/pasos/paso/${pasoId}/musica/${musicaId}`,
        {},
        {
          headers,
        }
      )
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  deletePasoMusica(idPaso: string, idMusica: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/pasos/paso/${idPaso}/musica/${idMusica}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la musica';
          return throwError(errorMessage);
        })
      );
  }
}
