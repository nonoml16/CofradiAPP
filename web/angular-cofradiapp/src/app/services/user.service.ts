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
import { UserDetails } from '../models/user-details';
import { PutUserDTO } from '../models/put-user-dto';
import { Home } from '../models/home';

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

  getUser(id: string): Observable<UserDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<UserDetails>(`${this.apiBaseUrl}/user/web/${id}`, {
      headers,
    });
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

  updateUser(id: string, user: PutUserDTO): Observable<PutUserDTO> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http
      .put<PutUserDTO>(`${this.apiBaseUrl}/user/${id}`, user, { headers })
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }

  deleteUserHermandadFav(idUser: string, idHermandad: string): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/user/${idUser}/hermandad/${idHermandad}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la hermandad';
          return throwError(errorMessage);
        })
      );
  }

  addUserHermandadFav(
    idUser: string,
    idHermandad: string
  ): Observable<UserDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .post<UserDetails>(
        `${this.apiBaseUrl}/user/${idUser}/hermandad/${idHermandad}`,
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

  deleteUserCard(idUser: string, idCard: number): Observable<any> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .delete(`${this.apiBaseUrl}/user/${idUser}/card/${idCard}`, {
        headers,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al borrar la hermandad';
          return throwError(errorMessage);
        })
      );
  }

  addUserCard(idUser: string, idCard: number): Observable<UserDetails> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });
    return this.http
      .post<UserDetails>(
        `${this.apiBaseUrl}/user/${idUser}/card/${idCard}`,
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

  getHome(): Observable<Home> {
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
      'Content-Type': 'application/json',
    });
    return this.http.get<Home>(`${this.apiBaseUrl}/web/home`, {
      headers,
    });
  }
}
