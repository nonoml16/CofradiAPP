import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private apiBaseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    const authToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + authToken,
    });

    return this.http
      .post<any>(`${this.apiBaseUrl}/upload`, formData, { headers })
      .pipe(
        catchError((error) => {
          throw error;
        })
      );
  }
}
