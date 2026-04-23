import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.apiBaseUrl;
  constructor(private http: HttpClient) { }

  getAllActiveUser(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/users/active`)
  }
}
