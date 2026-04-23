import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../service/auth-service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-registration',
  imports: [FormsModule, RouterModule],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {
  username: string;
  useremail: string;
  password: string;
  confirmPassword: string;

  constructor(private authService: AuthService,
    private router: Router,
    private toastr: ToastrService) {
  }

  persistUser() {
    this.authService.persisUser({
      userName: this.username,
      userEmail: this.useremail,
      password: this.password
    }).subscribe({
      next: (response) => {
        this.toastr.success('User registered successfully!', 'Success');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.toastr.error('Failed to register user!', 'Error');
      },
    })
  }

  disableSubmitButton(): boolean {
    return !this.username || !this.useremail || !this.password || this.password !== this.confirmPassword;
  }
}
