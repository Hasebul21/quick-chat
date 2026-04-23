import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';
import { sockJsUrl } from '../ws.util';

@Injectable({
  providedIn: 'root'
})
export class SocketService {

  private url = sockJsUrl();
  private stompClient: any | undefined;
  isConnected = false;

  constructor() { }

  onConnect(): Observable<boolean> {
    return new Observable<boolean>((observer) => {
      if (this.isConnected) {
        observer.next(true);
        observer.complete();
        return;
      }
      const socket = new SockJS(this.url);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, () => {
        this.isConnected = true;
        observer.next(true);
        observer.complete();
      }, (error) => {
        observer.error(error);
      });
    });
  }

  subscribeToActiveUser(): Observable<any> {
    return new Observable(observer => {
      if (!this.stompClient || !this.isConnected) {
        observer.error("WebSocket not connected");
        return;
      }
      this.stompClient.subscribe('/topic/public/activeUsers', response => {
        const message = JSON.parse(response.body);
        observer.next(message);
      });
    });
  }

  subscribeTo(): Observable<any> {
    return new Observable(observer => {
      if (!this.stompClient || !this.isConnected) {
        observer.error("WebSocket not connected");
        return;
      }

      this.stompClient.subscribe(`/topic/public`, response => {
        const message = JSON.parse(response.body);
        observer.next(message);
      });
    });
  }

  sendMessageToNewUser(userName: string, userEmail: string) {
    if (!this.stompClient || !this.isConnected) {
      return;
    }
    this.stompClient.send('/app/addUser', {}, JSON.stringify(
      {
        senderName: userName,
        status: 'SENT'
      }
    ))
  }

  sendMessageToActiveUser() {
    if (!this.stompClient || !this.isConnected) {
      return;
    }
    this.stompClient.send('/app/getAllUser', {}, null)
  }
}
