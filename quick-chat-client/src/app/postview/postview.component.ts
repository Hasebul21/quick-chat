import { Component } from '@angular/core';
import { PostService } from '../service/post.service';
import { CommonModule } from '@angular/common';
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

@Component({
  selector: 'app-postview',
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
  templateUrl: './postview.component.html',
  styleUrls: ['./postview.component.scss']
})
export class PostviewComponent {

  currentPagePosts: any[] = [];
  currentPage: number = 1;
  totalPages: number = 10; 
  filteredPosts: any[] = [];
  showFilterOptions = false;

  // Filter model to store filter options
  filter = {
    username: '',
    content: '',
  };

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.postService.getAllPosts().subscribe((posts) => {
      // Paginate posts, showing 8 posts per page
      const startIndex = (this.currentPage - 1) * 8;
      const endIndex = startIndex + 8;
      this.currentPagePosts = posts.slice(startIndex, endIndex);
    });
  }

  openFilterOptions(): void {
    this.showFilterOptions = !this.showFilterOptions;
  }

  // Apply filter based on selected criteria
  applyFilter(): void {
    this.filteredPosts = this.currentPagePosts.filter((post) => {
      const matchesUsername = this.filter.username
        ? post.creatorName.toLowerCase().includes(this.filter.username.toLowerCase())
        : true;
      const matchesContent = this.filter.content
        ? post.content.toLowerCase().includes(this.filter.content.toLowerCase())
        : true;
      return matchesUsername && matchesContent;
    });
  }

   // Reset filter criteria
   resetFilter(): void {
    this.filter.username = '';
    this.filter.content = '';
    this.filteredPosts = [...this.currentPagePosts]; // Reset to all posts
    this.showFilterOptions = false;
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadPosts();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadPosts();
    }
  }

  editPost(post: any): void {
    console.log('Edit post:', post);
    // Implement your logic for editing the post (e.g., open a form for editing)
  }

  deletePost(post: any): void {
    console.log('Delete post:', post);
    // Implement your logic for deleting the post (e.g., call service to delete)
  }

}
