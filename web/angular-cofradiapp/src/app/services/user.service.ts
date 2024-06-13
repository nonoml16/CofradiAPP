import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { UserNav } from '../models/user-nav';
import { UserItemList } from '../models/user-item-list';
import { PostUserDTO } from '../models/post-user-dto';

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

  deleteUser(id: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/user/${id}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar el usuario';
          return throwError(errorMessage);
        })
      );
  }

  addUser(user: PostUserDTO): Observable<PostUserDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .post<PostUserDTO>(
        `${this.apiBaseUrl}/user/nuevo`,
        {
          username: user.username,
          password: user.password,
          email: user.email,
          nombre: user.nombre,
          apellidos: user.apellidos,
          avatar: user.avatar,
          hermandadId: user.hermandadId,
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
