import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

@Component({
  selector: 'app-profile-section',
  imports: [
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatSelectModule,
    MatMenuModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MatSnackBarModule
  ],
  templateUrl: './profile-section.component.html',
  styleUrl: './profile-section.component.scss'
})
export class ProfileSectionComponent implements OnChanges {
  @Input() loggedInUser: any = null;

  private stompClient: any | undefined;
  private isSubscribed: boolean = false;
  trendingPostsCount: number = 0;

  ngOnChanges(): void {
    console.log(this.isSubscribed);
    console.log(this.loggedInUser);
    this.trendingPostsCount = this.loggedInUser.publishedPostCount;
    if (!this.isSubscribed) {
      console.log('isSubscribed');
      this.connectSocket();
    }
  }


  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.isSubscribed = true;
      console.log('Connected to WebSocket for post count');
      this.stompClient.subscribe(`/user/${this.loggedInUser.id}/post-count/queue`, response => {
        console.log(response);
        this.trendingPostsCount = JSON.parse(response.body);
      });
    }, (error) => {
      console.log(error);
    });
  }
}
