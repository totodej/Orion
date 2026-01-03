import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/post';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  private apiUrl = 'http://localhost:3001/api/posts';

  constructor(private http: HttpClient) {}

  createPost(data: any) {
    return this.http.post(`${this.apiUrl}`, data);
  }

  getAllPosts(sort: string) {
    return this.http.get<Post[]>(`${this.apiUrl}?sort=${sort}`);
  }

  getPostById(postId: number) {
    return this.http.get<Post>(`${this.apiUrl}/${postId}`);
  }
}
