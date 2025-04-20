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
  searchQuery = '';
  currentPage: number = 1;
  postsPerPage: number = 6;
  totalPages: number = Math.ceil(this.trendingPosts.length / this.postsPerPage);

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

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  get filteredPosts() {
    if (!this.searchQuery.trim()) {
      return this.trendingPosts;
    }
    return this.trendingPosts.filter(post =>
      post.user.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  get currentPagePosts() {
    const startIndex = (this.currentPage - 1) * this.postsPerPage;
    const endIndex = startIndex + this.postsPerPage;
    return this.trendingPosts.slice(startIndex, endIndex);
  }

  searchPosts() {
    // Intentionally left empty as we're using a getter for filteredPosts
  }

  connectSocket() {
      const socket = new SockJS('http://localhost:8080/ws');
      this.stompClient = Stomp.over(socket);
      console.log('Connecting to WebSocket server for trending posts...');
      this.stompClient.connect({}, () => {
        this.isSubscribed = true;
        console.log('Connected to WebSocket server for trending posts');
        this.stompClient.subscribe(`/topic/public/treding-post`, response => {
          this.trendingPosts = JSON.parse(response.body);
          console.log(this.trendingPosts);
        });
        this.postService.getMostLikedPost().subscribe();
      }, (error) => {
        console.log(error);
      });
    }

}
