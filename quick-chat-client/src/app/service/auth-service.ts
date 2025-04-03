import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080/auth/users'
  constructor(private http: HttpClient) { }

  persisUser(user : any): Observable<any>{
    return this.http.post<any>(this.url, user);
  }

  getAllUser(): Observable<any[]>{
    return this.http.get<any[]>(this.url);
  }

  getUserById(id: number): Observable<any>{
    return this.http.get<any>(`${this.url}/${id}`);
  }

  getUserByUserNameAndPassword(useremail: string, password: string): Observable<any>{
    return this.http.get<any>(`${this.url}/${useremail}/${password}`)
  }
}
