import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.apiBaseUrl}/auth/login`, { username, password });
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token'); // Obtén el token del localStorage
    return !!token; // Devuelve true si hay un token, false si no lo hay
  }
}
