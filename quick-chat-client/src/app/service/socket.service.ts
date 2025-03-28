import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class SocketService {

  private url = 'http://localhost:8080/ws'
  private stompClient: any | undefined;
  isConnected = false;

  constructor() { }

  onConnect(): Observable<boolean> {
    return new Observable<boolean>((observer) => {
      if (this.isConnected) {
        observer.next(true);  // ✅ Emit true if already connected
        observer.complete();
        return;
      }
      const socket = new SockJS(this.url);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, () => {
        console.log('✅ WebSocket connected');
        this.isConnected = true;
        observer.next(true);  // ✅ Emit success
        observer.complete();
      }, (error) => {
        console.error('❌ WebSocket connection error:', error);
        observer.error(error);  // ✅ Emit error if connection fails
      });
    });
  }

  subscribeToActiveUser(): Observable<any> {
    return new Observable(observer => {
      if (!this.stompClient || !this.isConnected) {
        console.error("❌ WebSocket not connected. Cannot subscribe.");
        observer.error("WebSocket not connected");
        return;
      }

      console.log(this.stompClient);
      this.stompClient.subscribe('/topic/public/activeUsers', response => {
        console.log(response)
        const message = JSON.parse(response.body);
        observer.next(message);
      });
    });
  }

  subscribeTo(): Observable<any> {
    return new Observable(observer => {
      if (!this.stompClient || !this.isConnected) {
        console.error("❌ WebSocket not connected. Cannot subscribe.");
        observer.error("WebSocket not connected");
        return;
      }

      this.stompClient.subscribe(`/topic/public`, response => {
        console.log('fetching user')
        console.log(response)
        const message = JSON.parse(response.body);
        observer.next(message);
      });
    });
  }

  sendMessageToNewUser(userName: string, userEmail: string) {
    console.log('sending')
    console.log(this.stompClient);
    console.log(this.isConnected)
    if (!this.stompClient || !this.isConnected) {
      console.error("❌ WebSocket not connected. Cannot send message.");
      return;
    }
    console.log(this.stompClient);
    this.stompClient.send('/app/addUser', {}, JSON.stringify(
      { 
        senderName: userName, 
        status : 'SENT' 
      }
    ))
  }

  sendMessageToActiveUser() {
    console.log('sending')
    console.log(this.stompClient);
    if (!this.stompClient || !this.isConnected) {
      console.error("❌ WebSocket not connected. Cannot send message.");
      return;
    }
    console.log(this.stompClient);
    this.stompClient.send('/app/getAllUser', {}, null)
  }
}
