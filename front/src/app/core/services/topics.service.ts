import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topic } from '../models/topic';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private apiUrl = 'http://localhost:3001/api/topics';

  constructor(
    private http: HttpClient,
  ) { }

  public getAllTopics() {
    return this.http.get<Topic[]>(this.apiUrl);
  }

}
