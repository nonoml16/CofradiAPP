
import { Injectable } from '@angular/core';
import { Observable, catchError, tap } from 'rxjs';
import { Router } from '@angular/router';
import { LoginSuccessResponse } from '../../models/login-success';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    private apiUrl = `http://localhost:8080/auth`;
    private token = 'token';
    private username = 'username';

    constructor(private http: HttpClient, private router: Router) { }

    login(username: string, password: string): Observable<LoginSuccessResponse> {
        return this.http.post<LoginSuccessResponse>(`${this.apiUrl}/login`, {
            username: username,
            password: password,
        }, {
            headers: {
                'Content-Type': 'application/json',
            },
        }).pipe(
            tap(response => {
                localStorage.setItem(this.username, response.username);
                localStorage.setItem(this.token, response.token);
                this.router.navigate(['/admin/home']); 
            }),
            catchError(error => {
                throw error;
            })
        );
    }

    logout(): void {
        localStorage.removeItem(this.token);
        this.router.navigate(['/login']);
    }

    isLoggedIn() {
        return !!localStorage.getItem(this.token);
    }
}
