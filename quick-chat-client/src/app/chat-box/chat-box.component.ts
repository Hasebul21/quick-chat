import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-chat-box',
  imports: [FormsModule, CommonModule],
  templateUrl: './chat-box.component.html',
  styleUrl: './chat-box.component.scss'
})
export class ChatBoxComponent implements OnChanges {

  @Input() selectedUser: any;
  @Input() loginUser: any;
  content: string;
  private stompClient: any | undefined;
  recivedMessage: string;
  messages: any[] = [];
  private isSubscribed: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.selectedUser);
    if (this.selectedUser && this.selectedUser.userId && !this.isSubscribed) {
      this.connectSocket();
    }
  }

  ngOnDestroy(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
      console.log('Disconnected from WebSocket');
    }
  }


  onMessageSent() {
    const tmp = this.content;
    this.content = '';
    this.addMessageToChat({
      senderName: this.loginUser.userName,
      content: tmp,
      time: new Date().toLocaleTimeString(),
    }, 'right');

    this.sendMessage(tmp);
  }

  sendMessage(content: string) {
    if (this.stompClient) {
      this.stompClient.send('/app/privateMessage', { receipt: 'message-receipt' },
        JSON.stringify({
          senderName: this.loginUser.userName,
          senderId: this.loginUser.userId,
          reciverName: this.selectedUser.userName,
          reciverId: this.selectedUser.userId,
          content: content,
          status: 'SENT'
        }));
    }
  }

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.isSubscribed = true;
      this.stompClient.subscribe(`/user/${this.loginUser.userId}/message/queue`, response => {
        const received = JSON.parse(response.body);
        this.recivedMessage = received;
        this.addMessageToChat(received, 'left');
      });
    }, (error) => {
      console.log(error);
    });
  }

  addMessageToChat(message: any, direction: any) {
    this.messages.push({
      senderName: message.senderName,
      content: message.content,
      time: new Date().toLocaleTimeString(),
      direction: direction
    });
  }
}
