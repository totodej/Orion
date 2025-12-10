import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private apiUrl = 'http://localhost:3001/api/subscriptions';

  constructor(private http: HttpClient) { }

  subscribe(topicId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${topicId}`, {});
  }

  unsubscribe(topicId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${topicId}`);
  }

  getMySubscriptions(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }
}
