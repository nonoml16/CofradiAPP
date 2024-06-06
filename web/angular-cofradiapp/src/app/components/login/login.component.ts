import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  error: string = '';

  constructor(private authService: AuthService) { }

  onSubmit(): void {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        const user = response.user;
        const token = response.token;
        
        localStorage.setItem('token', token);
      },
      error => {
        this.error = 'Usuario o contraseña incorrectos';
      }
    );
  }
}
