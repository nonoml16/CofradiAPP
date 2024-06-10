import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { HermandadItemList } from '../models/hermandad-item-list';

@Injectable({
  providedIn: 'root'
})
export class HermandadService {
  
  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) { }

  getHermandadList(dia: string): Observable<HermandadItemList[]>{
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + authToken
    });
    return this.http.get<HermandadItemList[]>(`${this.apiBaseUrl}/hermandades/web/${dia}`, {headers})
    .pipe(catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Error al cargar el usuario';
      return throwError(errorMessage);
    }));
  }
}
