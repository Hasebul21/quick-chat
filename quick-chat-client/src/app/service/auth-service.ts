import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080/auth/user'
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

  getUserByUserNameAndPassword(userEmail: string, password: string): Observable<any>{
    return this.http.post<any>(`${this.url}/login`, { userEmail, password });
  }

  setLoggedInUser(user: any): void {
    sessionStorage.setItem('loggedInUser', JSON.stringify(user));
    console.log(sessionStorage);
  }

  getLoggedInUser(): any {
    const user = sessionStorage.getItem('loggedInUser');
    return user ? JSON.parse(user) : null;
  }

  removeLoggedInUser(): void {
    sessionStorage.removeItem('loggedInUser');
  }
}
