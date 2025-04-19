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
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

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
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './postview.component.html',
  styleUrls: ['./postview.component.scss']
})
export class PostviewComponent {

  currentPage: number = 1;
  totalPages: number = 10;
  allFilteredPosts: any[] = []; // Store full filtered results
  filteredPosts: any[] = [];
  showFilterOptions = false;

  filter = {
    creatorName: null,
    content: null,
    likeCount: {
      gte: null,
      lte: null
    },
    dislikeCount: {
      gte: null,
      lte: null
    },
    createdDate: {
      gte: null,
      lte: null
    },
    updatedDate: {
      gte: null,
      lte: null
    }
  };

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.postService.getAllPosts().subscribe((posts) => {
      const startIndex = (this.currentPage - 1) * 8;
      const endIndex = startIndex + 8;
      this.filteredPosts = posts.slice(startIndex, endIndex);
      this.totalPages = Math.ceil(posts.length / 8);
    });
  }

  openFilterOptions(): void {
    this.showFilterOptions = !this.showFilterOptions;
  }

  applyFilter(): void {
    if (this.filter.createdDate?.gte) {
      this.filter.createdDate.gte = new Date(this.filter.createdDate.gte).toISOString();
    }
    if (this.filter.createdDate?.lte) {
      this.filter.createdDate.lte = new Date(this.filter.createdDate.lte).toISOString();
    }

    if (this.filter.updatedDate?.gte) {
      this.filter.updatedDate.gte = new Date(this.filter.updatedDate.gte).toISOString();
    }
    if (this.filter.updatedDate?.lte) {
      this.filter.updatedDate.lte = new Date(this.filter.updatedDate.lte).toISOString();
    }

    console.log('Filter criteria:', this.filter);

    this.postService.getPostsByFilter(this.filter).subscribe((posts) => {
      this.allFilteredPosts = posts;
      this.totalPages = Math.ceil(posts.length / 8);
      this.currentPage = 1;
      this.updateFilteredPosts();
    });
  }

  updateFilteredPosts(): void {
    const startIndex = (this.currentPage - 1) * 8;
    const endIndex = startIndex + 8;
    this.filteredPosts = this.allFilteredPosts.slice(startIndex, endIndex);
  }

  resetFilter(): void {
    this.filter = {
      creatorName: null,
      content: null,
      likeCount: {
        gte: null,
        lte: null
      },
      dislikeCount: {
        gte: null,
        lte: null
      },
      createdDate: {
        gte: null,
        lte: null
      },
      updatedDate: {
        gte: null,
        lte: null
      }
    };
    this.allFilteredPosts = [];
    this.currentPage = 1;
    this.totalPages = 10;
    this.showFilterOptions = false;
    this.loadPosts();
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.allFilteredPosts.length ? this.updateFilteredPosts() : this.loadPosts();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.allFilteredPosts.length ? this.updateFilteredPosts() : this.loadPosts();
    }
  }

  editPost(post: any): void {
    console.log('Edit post:', post);
    // Implement editing logic here
  }

  deletePost(post: any): void {
    console.log('Delete post:', post);
    // Implement delete logic here
  }
}
