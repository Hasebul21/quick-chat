import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent {
  title = '';
  location = '';
  portfolio = '';
  instagram = '';
  bio = '';
  skills = '';
  hobbies = '';
  loggedInUser: any = null;

  constructor(private authService: AuthService,
    private toastr: ToastrService,
    private router : Router
  ) {}

  ngOnInit() {
    this.loggedInUser = this.authService.getLoggedInUser();
    console.log(this.loggedInUser);
  }

  updateProfile() {
    const profileData = {
      professionalTitle: this.title,
      location: this.location,
      portfolio: this.portfolio,
      instagram: this.instagram,
      bio: this.bio,
      skills: this.skills,
      hobbies: this.hobbies
    };
    console.log('Updating profile with:', profileData);
    this.authService.updateUserProfile(this.loggedInUser.id, profileData).subscribe({
      next: (response) => {
        console.log('Profile updated successfully', response);
        this.authService.setLoggedInUser(response);
        this.toastr.success('Profile updated successfully!', 'Success');
        this.router.navigate(['/home']);
      },
      error: (error) => {
        this.toastr.error('Failed to update profile.', 'Error');
      }
    });
  }
}