import { CommonModule } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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
import { NavbarComponent } from '../navbar/navbar.component';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { sockJsUrl } from '../ws.util';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-trending-post',
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
  ],
  templateUrl: './trending-post.component.html',
  styleUrl: './trending-post.component.scss'
})
export class TrendingPostComponent implements OnInit, OnChanges {

  private stompClient: any | undefined;
  private isSubscribed: boolean = false;
  trendingPosts: any[] = [];
  isLoading: boolean = true;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    if (!this.isSubscribed) {
      this.connectSocket();
    }
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (!this.isSubscribed) {
      this.connectSocket();
    }
  }

  connectSocket() {
    const socket = new SockJS(sockJsUrl());
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.isSubscribed = true;
      this.stompClient.subscribe(`/topic/public/treding-post`, response => {
        const realPosts = JSON.parse(response.body);
        this.trendingPosts = realPosts;
        this.isLoading = false;
      });
      this.postService.getMostLikedPost().subscribe();
    }, (error) => {
      console.log(error);
    });
  }

}
