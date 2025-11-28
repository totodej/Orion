import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:3001/api/user';

  constructor(
    private HttpClient: HttpClient,
  ) { }

  public getUserById(id: number) {
    return this.HttpClient.get(`${this.apiUrl}/${id}`);
  }
}
