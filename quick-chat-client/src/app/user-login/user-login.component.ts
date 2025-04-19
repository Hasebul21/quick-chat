import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { Router, RouterModule } from '@angular/router';
import { StompService } from '../service/stomp.service';

@Component({
  selector: 'app-user-login',
  imports: [FormsModule, RouterModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.scss'
})
export class UserLoginComponent {
    username: string = 'rithy@quickchat.com';
    password: string = '12345';

    constructor(private auth : AuthService, 
      private router : Router,
      private stompService : StompService
    ){}

    login(){
      this.auth.getUserByUserNameAndPassword(this.username, this.password).subscribe({
        next: (response)=>{
           console.log(response);
           this.auth.setLoggedInUser(response);
            this.stompService.connect(response);
           this.router.navigate(['/home']);
        },
        error: ()=>{

        }
      })
  
    }
}
