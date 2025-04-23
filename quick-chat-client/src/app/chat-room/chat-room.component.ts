import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { UserStatusComponent } from '../user-status/user-status.component';
import { ChatBoxComponent } from '../chat-box/chat-box.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserLoginComponent } from '../user-login/user-login.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { StompService } from '../service/stomp.service';
import { AuthService } from '../service/auth-service';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
  selector: 'app-chat-room',
  standalone: true,
  imports: [UserStatusComponent, ChatBoxComponent, FormsModule, CommonModule, RouterModule, NavbarComponent],
  templateUrl: './chat-room.component.html',
  styleUrl: './chat-room.component.scss'
})
export class ChatRoomComponent implements OnChanges, OnInit {
  private stompClient: any | undefined;
  isSelected = false;
  userName: string | undefined;
  userEmail: string | undefined;
  activeUsers: any[] = [];
  loginUser: any | undefined;
  @Input() selectedUser: any;
  login = false;
  isConnected = false;

  constructor(private cdr: ChangeDetectorRef, 
    private stompService : StompService, private authService: AuthService) {
     
  }

  ngOnInit(): void {
    this.loginUser = this.authService.getLoggedInUser();
    this.connectSocket();
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  connectSocket() {
    if (this.stompClient && this.stompClient.connected) {
      return;
    }
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.isSelected = true;
      this.isConnected = true;
      this.stompClient.subscribe(`/topic/public/activeUsers`, response => {
        this.activeUsers = [...JSON.parse(response.body)];
      });

      this.stompClient.send('/app/chat/join', {}, JSON.stringify({
        id: this.loginUser.id,
        userName: this.loginUser.userName,
        userEmail: this.loginUser.userEmail
      }));
    }, (error) => {
      console.log(error);
    });
  };

  onUserChangeEvent(user: any) {
    this.selectedUser = user;
  }
}
