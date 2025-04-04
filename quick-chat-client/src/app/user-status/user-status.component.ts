import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { UserService } from '../service/user.service';
import { CommonModule } from '@angular/common';
import { SocketService } from '../service/socket.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

@Component({
  selector: 'app-user-status',
  imports: [CommonModule],
  templateUrl: './user-status.component.html',
  styleUrl: './user-status.component.scss',
})
export class UserStatusComponent implements OnChanges {
  @Input() loginUser: any;
  @Input() userList: any[] = [];
  @Input() activeUsers: any[] = [];
  @Output() selectedUserEvent: EventEmitter<any> = new EventEmitter<any>();
  private stompClient: any | undefined;
  private isSubscribed: boolean = false;

  constructor(
    private userService: UserService,
    private sockeService: SocketService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('ngOnChanges called');
    //console.log(this.activeUsers);
    if (!this.isSubscribed)
      this.connectSocket();
  }

  selectUser(selectedUser: any) {
    this.selectedUserEvent.emit(selectedUser);
  }

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.isSubscribed = true;

      this.stompClient.subscribe(`/topic/public/loggedInUser`, response => {
        this.activeUsers = [...this.activeUsers, JSON.parse(response.body)]
        console.log(this.activeUsers);
      });

    }, (error) => {
      console.log(error);
    });
  }
  isUserActive(userEmail: string): boolean {
    return this.activeUsers.some(activeUser => activeUser.userEmail === userEmail);
  }

}
