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
  @Input() activeUsers: any[] = [];
  @Output() selectedUserEvent: EventEmitter<any> = new EventEmitter<any>();
  private stompClient: any | undefined;

  constructor(
    private userService: UserService,
    private sockeService: SocketService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.loginUser);
    console.log(this.activeUsers);
  }

  selectUser(selectedUser: any) {
    this.selectedUserEvent.emit(selectedUser);
  }
}
