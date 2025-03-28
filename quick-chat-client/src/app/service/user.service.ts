import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  getAllActiveUser(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/users/active`)
  }
}
