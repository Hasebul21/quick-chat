import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { UserStatusComponent } from '../user-status/user-status.component';
import { ChatBoxComponent } from '../chat-box/chat-box.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserLoginComponent } from '../user-login/user-login.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

@Component({
  selector: 'app-chat-room',
  standalone: true,
  imports: [UserStatusComponent, ChatBoxComponent, FormsModule, CommonModule, UserLoginComponent, RouterModule, HomeComponent],
  templateUrl: './chat-room.component.html',
  styleUrl: './chat-room.component.scss'
})
export class ChatRoomComponent {
  private stompClient: any | undefined;
  isSelected = false;
  userName: string | undefined;
  userEmail: string | undefined;
  activeUsers: any[] = [];
  loginUser: any | undefined = {};
  @Input() selectedUser: any;
  login = false;




  constructor(private cdr: ChangeDetectorRef) {
    // this.connectSocket();
    this.loginUser = {
      id: 1,
      userName: 'Alice Smith',
      userEmail: 'alice@example.com'
    };

    this.activeUsers = [
      {
        id: 2,
        userName: 'Bob Johnson',
        userEmail: 'bob@example.com'
      },
      {
        id: 3,
        userName: 'Charlie Rose',
        userEmail: 'charlie@example.com'
      },
      {
        id: 4,
        userName: 'Diana Prince',
        userEmail: 'diana@example.com'
      }
    ];
  }

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      console.log("Connected as " + this.userName);
      this.isSelected = true;
      this.stompClient.send('/app/addUser', { receipt: 'message-receipt' },
        JSON.stringify({
          id: this.loginUser.id,
          userName: this.loginUser.userName,
          userEmail: this.loginUser.userEmail,
        }
        ));

      this.stompClient.subscribe(`/topic/public`, response => {
        const tmp = JSON.parse(response.body);
        if (this.loginUser.userEmail === tmp.userEmail)
          this.loginUser = JSON.parse(response.body)
      });

      this.stompClient.subscribe(`/topic/public/activeUsers`, response => {
        this.activeUsers = JSON.parse(response.body)
      });

    }, (error) => {
      console.log(error);
    });
  };

  disConnectSocket() {
    this.isSelected = false;
    this.userEmail = '';
    this.userName = '';
    this.loginUser = undefined;
  }

  onUserChangeEvent(user: any) {
    this.selectedUser = user;
  }

  setloginUser(user: any) {
    this.loginUser = user;
    this.connectSocket();
  }

  setLoginUserName() {

  }
}
