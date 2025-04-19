import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';
import { BehaviorSubject, ReplaySubject, Subject } from 'rxjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class StompService {

  private stompClient: any | undefined;
  private connected = false;
  // Subjects to share data with components
  activeUsers$ = new BehaviorSubject<any>([]);
  trendingPosts$ = new Subject<any>();
  updatedPosts$ = new Subject<any>();
  privateMessages$ = new Subject<any>();

  constructor() {
  }

  
  connect(loginUser : any) {
    console.log('Connecting to STOMP...', this.connected);
    if (this.connected) return;

    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.connected = true;
      console.log('[STOMP] Connected', this.connected);
      this.stompClient.send('/app/chat/join', { receipt: 'message-receipt' },
        JSON.stringify({
          id: loginUser.id,
          userName: loginUser.userName,
          userEmail: loginUser.userEmail,
        }
        ));
    }, (error) => {
      console.log(error);
    });
  }


  private subscribeToTopics(loginUserId : any) {
    // this.stompClient.subscribe('/topic/public/activeUsers', (message: any) => {
    //   console.log('Active users: in repo', message.body);
    //   this.activeUsers$.next(JSON.parse(message.body));
    //   console.log('[STOMP] activeUsers$ emitted');
    // });

    this.stompClient.subscribe(`/user/${loginUserId}/message/queue`, (message: any) => {
      this.privateMessages$.next(JSON.parse(message.body));
    });
  }
  // Optional: To gracefully disconnect
  disconnect() {
    if (this.stompClient && this.connected) {
      this.stompClient.deactivate();
      this.connected = false;
      console.log('[STOMP] Disconnected');
    }
  }

  getStompClient() {
    return this.stompClient;
  }
}
