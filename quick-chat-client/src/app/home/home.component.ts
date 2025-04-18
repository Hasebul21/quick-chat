import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatMenuModule } from '@angular/material/menu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
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
    RouterModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  searchQuery = '';

  posts = [
    {
      user: 'Alice Smith',
      avatarUrl: 'https://i.pravatar.cc/150?img=1',
      timestamp: 'April 18, 2025',
      content: 'Loving the new Angular features in version 17!',
      likes: 23,
      dislikes: 3
    },
    {
      user: 'Bob Johnson',
      avatarUrl: 'https://i.pravatar.cc/150?img=2',
      timestamp: 'April 17, 2025',
      content: 'Check out my latest blog post on UI/UX trends.',
      likes: 18,
      dislikes: 1
    },
    {
      user: 'Charlie Rose',
      avatarUrl: 'https://i.pravatar.cc/150?img=3',
      timestamp: 'April 16, 2025',
      content: 'Angular Material makes life so much easier!',
      likes: 34,
      dislikes: 5
    },
    {
      user: 'Diana Prince',
      avatarUrl: 'https://i.pravatar.cc/150?img=4',
      timestamp: 'April 15, 2025',
      content: 'Finally deployed my first full-stack app with Angular and Firebase!',
      likes: 45,
      dislikes: 0
    },
    {
      user: 'Ethan Hunt',
      avatarUrl: 'https://i.pravatar.cc/150?img=5',
      timestamp: 'April 14, 2025',
      content: 'Can anyone recommend resources to learn RxJS deeply?',
      likes: 12,
      dislikes: 2
    },
    {
      user: 'Fiona Gallagher',
      avatarUrl: 'https://i.pravatar.cc/150?img=6',
      timestamp: 'April 13, 2025',
      content: 'Just redesigned my portfolio using Angular animations ✨',
      likes: 29,
      dislikes: 1
    },
    {
      user: 'George Miller',
      avatarUrl: 'https://i.pravatar.cc/150?img=7',
      timestamp: 'April 12, 2025',
      content: 'Struggling with performance issues in large Angular apps. Tips?',
      likes: 8,
      dislikes: 6
    },
    {
      user: 'Hannah Lee',
      avatarUrl: 'https://i.pravatar.cc/150?img=8',
      timestamp: 'April 11, 2025',
      content: 'Angular + Tailwind CSS = design magic 💫',
      likes: 39,
      dislikes: 2
    },
    {
      user: 'Ian Wright',
      avatarUrl: 'https://i.pravatar.cc/150?img=9',
      timestamp: 'April 10, 2025',
      content: 'Working on a real-time chat app with Angular and Socket.IO 🚀',
      likes: 31,
      dislikes: 0
    },
    {
      user: 'Jane Doe',
      avatarUrl: 'https://i.pravatar.cc/150?img=10',
      timestamp: 'April 9, 2025',
      content: 'What are your favorite Angular performance optimization techniques?',
      likes: 21,
      dislikes: 4
    },
    {
      user: 'James Bond',
      avatarUrl: 'https://i.pravatar.cc/150?img=11',
      timestamp: 'April 8, 2025',
      content: 'New Angular CLI update makes everything much smoother!',
      likes: 50,
      dislikes: 2
    },
    {
      user: 'Lily Evans',
      avatarUrl: 'https://i.pravatar.cc/150?img=12',
      timestamp: 'April 7, 2025',
      content: 'Anyone tried integrating Angular with GraphQL? Thoughts?',
      likes: 12,
      dislikes: 3
    },
    {
      user: 'Mike Tyson',
      avatarUrl: 'https://i.pravatar.cc/150?img=13',
      timestamp: 'April 6, 2025',
      content: 'Building a REST API with Angular and NestJS!',
      likes: 35,
      dislikes: 4
    }
  ];

  currentPage: number = 1;
  postsPerPage: number = 6;
  totalPages: number = Math.ceil(this.posts.length / this.postsPerPage);

  get currentPagePosts() {
    const startIndex = (this.currentPage - 1) * this.postsPerPage;
    const endIndex = startIndex + this.postsPerPage;
    return this.posts.slice(startIndex, endIndex);
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


  users = [
    {
      name: 'Alice Smith',
      role: 'Designer',
      avatar: 'https://i.pravatar.cc/301',
      email: 'alice.smith@example.com',
      friends: ['Bob Johnson', 'Charlie Rose', 'Eve Watson']
    },
    {
      name: 'Bob Johnson',
      role: 'Engineer',
      avatar: 'https://i.pravatar.cc/302',
      email: 'bob.johnson@example.com',
      friends: ['Alice Smith', 'Charlie Rose', 'Daniel Scott']
    },
    {
      name: 'Charlie Rose',
      role: 'PM',
      avatar: 'https://i.pravatar.cc/303',
      email: 'charlie.rose@example.com',
      friends: ['Alice Smith', 'Bob Johnson', 'Eve Watson']
    },
    {
      name: 'Daniel Scott',
      role: 'QA Analyst',
      avatar: 'https://i.pravatar.cc/304',
      email: 'daniel.scott@example.com',
      friends: ['Bob Johnson', 'Eve Watson']
    },
    {
      name: 'Eve Watson',
      role: 'DevOps Engineer',
      avatar: 'https://i.pravatar.cc/305',
      email: 'eve.watson@example.com',
      friends: ['Alice Smith', 'Charlie Rose', 'Daniel Scott']
    }
  ];

  newPostContent: string = '';

  postPublicly() {
    if (this.newPostContent.trim()) {
      const newPost = {
        user: 'John Doe',
        avatarUrl: 'https://i.pravatar.cc/300?img=7',
        timestamp: new Date().toLocaleString(),
        content: this.newPostContent,
        likes: 0,
        dislikes: 0,
      };
      this.filteredPosts.unshift(newPost);
      this.newPostContent = '';
    }
  }


  get filteredPosts() {
    if (!this.searchQuery.trim()) {
      return this.posts;
    }
    return this.posts.filter(post =>
      post.user.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  searchPosts() {
    // Intentionally left empty as we're using a getter for filteredPosts
  }
}
