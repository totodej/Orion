import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from '../models/message';

@Injectable({
  providedIn: 'root',
})
export class MessagesService {
  private apiUrl = 'http://localhost:3001/api';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  createMessage(postId: number, message: string) {
    return this.http.post<Message>(
      `${this.apiUrl}/posts/${postId}/messages`,
      { message },
      { headers: this.getAuthHeaders() }
    );
  }

  getMessages(postId: number) {
    return this.http.get<Message[]>(`${this.apiUrl}/posts/${postId}/messages`, {
      headers: this.getAuthHeaders(),
    });
  }
}
