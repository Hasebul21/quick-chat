import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private url = environment.apiBaseUrl;
  constructor(private http: HttpClient) { }

  getAllChats(senderId: string, receiverId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/chatMessage/${senderId}/${receiverId}`)
  }
}
