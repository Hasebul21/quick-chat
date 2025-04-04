import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-registration',
  imports: [FormsModule],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {
  username: string;
  useremail: string;
  password: string;
  confirmPassword: string;

  constructor(private authService : AuthService, private router: Router){}

  persistUser(){
    this.authService.persisUser({
      username: this.username,
      useremail: this.useremail,
      password: this.password
    }).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Error persisting user:', err);
      },
    })
  }
}
