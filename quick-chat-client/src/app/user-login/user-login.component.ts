import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth-service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-login',
  imports: [FormsModule, RouterModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.scss'
})
export class UserLoginComponent {
    username: string;
    password: string;
    @Output() selectedUserEvent : EventEmitter<any> = new EventEmitter<any>();
    @Output() selectedUsernameEvent : EventEmitter<any> = new EventEmitter<any>();

    constructor(private auth : AuthService){}

    login(){
      this.auth.getUserByUserNameAndPassword(this.username, this.password).subscribe({
        next: (response)=>{
          this.selectedUserEvent.emit(response);
          this.selectedUsernameEvent.emit(this.username);
        },
        error: ()=>{

        }
      })
  
    }
}
