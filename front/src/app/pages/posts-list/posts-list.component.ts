import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/core/models/post';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-posts-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.scss'],
})
export class PostsListComponent implements OnInit {
  posts: Post[] = [];
  sortDirection: 'asc' | 'desc' = 'desc';

  constructor(private postsService: PostsService, private router: Router) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  openPost(postId: number): void {
    this.router.navigate(['/post', postId]);
  }

  loadPosts(): void {
    this.postsService.getAllPosts(this.sortDirection).subscribe({
      next: (data) => {
        this.posts = data;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des posts :", err);
      },
    });
  }

  toggleSort(): void {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.loadPosts();
  }
}
