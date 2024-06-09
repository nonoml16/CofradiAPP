import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { UserNav } from '../models/user-nav';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) { }

  getAuthUserLite(): Observable<UserNav>{
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + authToken
    });
    return this.http.get<UserNav>(`${this.apiBaseUrl}/me-lite`, {headers}).pipe(catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Error al cargar el usuario';
      return throwError(errorMessage);
    })
  );
  }
}
