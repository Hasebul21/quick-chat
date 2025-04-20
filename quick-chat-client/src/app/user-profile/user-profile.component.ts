import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [FormsModule, NavbarComponent],
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
  profileImage: any = null;
  loggedInUser: any = null;

  constructor(private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit() {
    this.loggedInUser = this.authService.getLoggedInUser();
    console.log(this.loggedInUser);
  }

  updateProfile() {
    const formData = new FormData();
  
    if (this.title) formData.append('professionalTitle', this.title);
    if (this.location) formData.append('location', this.location);
    if (this.portfolio) formData.append('portfolio', this.portfolio);
    if (this.instagram) formData.append('instagram', this.instagram);
    if (this.bio) formData.append('bio', this.bio);
    if (this.skills) formData.append('skills', this.skills);
    if (this.hobbies) formData.append('hobbies', this.hobbies);
  
    if (this.profileImage) {
      formData.append('profileImage', this.profileImage);
    }
  
    console.log('Sending FormData:');
    for (let [key, val] of formData.entries()) {
      console.log(`${key}:`, val);
    }
  
    this.authService.updateUserProfile(this.loggedInUser.id, formData).subscribe({
      next: (response) => {
        console.log('Profile updated successfully', response);
        response.profileImage = `data:image/jpeg;base64,${response.profileImage}`;
        this.authService.setLoggedInUser(response);
        this.toastr.success('Profile updated successfully!', 'Success');
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Failed to update profile.', error);
        this.toastr.error('Failed to update profile.', 'Error');
      }
    });
  }
  

  onFileSelected(event: any) {
    this.profileImage = event.target.files[0];
    console.log('Selected file:', this.profileImage);

  }
}