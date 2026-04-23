import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { UserService } from '../service/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SocketService } from '../service/socket.service';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { DEFAULT_AVATAR } from '../mock-data';

@Component({
  selector: 'app-user-status',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-status.component.html',
  styleUrl: './user-status.component.scss',
})
export class UserStatusComponent implements OnChanges {
  @Input() loginUser: any;
  @Input() activeUsers: any[] = [];
  @Output() selectedUserEvent: EventEmitter<any> = new EventEmitter<any>();

  selectedUser: any = null;
  searchTerm: string = '';

  private stompClient: any | undefined;

  constructor(
    private userService: UserService,
    private sockeService: SocketService
  ) { }

  get filteredUsers(): any[] {
    const term = this.searchTerm.toLowerCase().trim();
    return this.activeUsers.filter(u =>
      u.userEmail !== this.loginUser?.userEmail &&
      (!term || u.userName?.toLowerCase().includes(term))
    );
  }

  ngOnChanges(changes: SimpleChanges): void { }

  selectUser(user: any) {
    this.selectedUser = user;
    this.selectedUserEvent.emit(user);
  }

  getProfileImageSrc(user: any): string {
    if (!user?.profileImage) {
      return DEFAULT_AVATAR;
    }
    if (user.profileImage.startsWith('http') || user.profileImage.startsWith('data:')) {
      return user.profileImage;
    }
    return `data:image/jpeg;base64,${user.profileImage}`;
  }

  onImgError(event: Event): void {
    (event.target as HTMLImageElement).src = DEFAULT_AVATAR;
  }
}
