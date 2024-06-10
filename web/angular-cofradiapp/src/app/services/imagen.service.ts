import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {

  private apiBaseUrl = 'http://localhost:8080';
  private token = 'token';

  constructor(private http: HttpClient) { }

  getImage(imagen: string): Observable<Blob>{
    const authToken = localStorage.getItem(this.token);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${authToken}`);
    return this.http.get(`${this.apiBaseUrl}/download/${imagen}`, { headers, responseType: 'blob' });
  }
}
