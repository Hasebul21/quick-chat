import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../service/auth-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, FormsModule, CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
   
  loggedInUser: any = null;
  constructor(private auth: AuthService,
    private toastr: ToastrService,
    private router : Router
  ) {

  }

  ngOnInit() {
    this.loggedInUser = this.auth.getLoggedInUser();
  }

  logout() {
    this.auth.logout(this.loggedInUser.userEmail).subscribe({
      next: (response) => {
        this.auth.removeLoggedInUser();
        this.loggedInUser = null;
        this.toastr.success('Logout successfully!', 'Success');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Logout failed', error);
      }
    });
  }
}
