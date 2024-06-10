import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { UserNav } from '../models/user-nav';
import { UserItemList } from '../models/user-item-list';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) {}

  getAuthUserLite(): Observable<UserNav> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<UserNav>(`${this.apiBaseUrl}/me-lite`, { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar el usuario';
          return throwError(errorMessage);
        })
      );
  }

  getUserList(): Observable<UserItemList[]> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .get<UserItemList[]>(`${this.apiBaseUrl}/users/web/`, { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al cargar los usuarios';
          return throwError(errorMessage);
        })
      );
  }
}