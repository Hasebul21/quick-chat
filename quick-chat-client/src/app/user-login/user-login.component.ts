import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { Router, RouterModule } from '@angular/router';
import { StompService } from '../service/stomp.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-login',
  imports: [FormsModule, RouterModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.scss'
})
export class UserLoginComponent {
    username: string = null;
    password: string = null;

    constructor(private auth : AuthService,
      private router : Router,
      private stompService : StompService,
      private toastr: ToastrService
    ){}

    login(){
      this.auth.getUserByUserNameAndPassword(this.username, this.password).subscribe({
        next: (response)=>{
           response.profileImage = `data:image/jpeg;base64,${response.profileImage}`;
           this.auth.setLoggedInUser(response);
           this.stompService.connect(response);
           this.toastr.success('User logged in successfully!', 'Success');
           this.router.navigate(['/home']);
        },
        error: ()=>{
          this.toastr.error('Failed to logged in user!', 'Error');
        }
      })

    }
}
