import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
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
  @Input() activeUsers: any[] = [];
  private stompClient: any | undefined;
  constructor(
    private userService: UserService,
    private sockeService: SocketService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
  }
}
