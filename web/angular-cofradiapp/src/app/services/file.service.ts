import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

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
          if (error.status === 413) {
            // Aquí puedes manejar el error específico de Payload Too Large (413)
            return throwError(
              'El archivo es demasiado grande. Por favor, selecciona un archivo más pequeño.'
            );
          } else {
            return throwError(
              'Error al subir el archivo. Por favor, intenta de nuevo más tarde.'
            );
          }
        })
      );
  }
}
