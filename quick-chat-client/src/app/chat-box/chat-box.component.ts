import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { ChatService } from '../service/chat.service';
import { forkJoin, map } from 'rxjs';

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

  constructor(private chatService : ChatService){}

  ngOnChanges(changes: SimpleChanges): void {
    if (this.selectedUser && !this.isSubscribed) {
      this.connectSocket();
    }
    forkJoin([
      this.chatService.getAllChats(this.loginUser.id, this.selectedUser.id),
      this.chatService.getAllChats(this.selectedUser.id, this.loginUser.id)
    ])
    .pipe(
      map(([messages1, messages2]) => [...messages1, ...messages2]) // Merge both responses
    )
    .subscribe({
      next: (response) => {
        console.log(response);
        console.log(this.loginUser, this.selectedUser);
        this.messages = response.map((message: any) => ({
          userName: message.senderName,
          content: message.content,
          time: new Date(message.createdOn),
          direction: message.senderId == this.loginUser.id ? 'right' : 'left'
        }));
  
        // Sort messages by time (ascending order)
        this.messages.sort((a, b) => a.time.getTime() - b.time.getTime());

        this.messages = this.messages.map(message => ({
          ...message,
          time: message.time.toLocaleTimeString() // Convert to readable format
        }));
  
        console.log(this.messages);
      },
      error: (err) => {
        console.error('Error fetching chat history:', err);
      },
    });
  }

  ngOnDestroy(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }


  onMessageSent() {
    const tmp = this.content;
    this.content = '';
    this.addMessageToChat({
      userName: this.loginUser.userName,
      content: tmp,
      time: new Date().toLocaleTimeString(),
    }, 'right');

    this.sendMessage(tmp);
  }

  sendMessage(content: string) {
    console.log(this.loginUser, this.selectedUser);
    if (this.stompClient) {
      this.stompClient.send('/app/privateMessage', { receipt: 'message-receipt' },
        JSON.stringify({
          senderName: this.loginUser.userName,
          senderId: this.loginUser.id,
          receiverId: this.selectedUser.id,
          receiverName: this.selectedUser.userName,
          content: content,
        }));
    }
  }

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.isSubscribed = true;
      this.stompClient.subscribe(`/user/${this.loginUser.id}/message/queue`, response => {
        const received = JSON.parse(response.body);
        this.recivedMessage = JSON.parse(response.body);
        this.addMessageToChat(received, 'left');
      });
    }, (error) => {
      console.log(error);
    });
  }

  addMessageToChat(message: any, direction: any) {
    console.log(message);
    this.messages.push({
      userName: message.userName,
      content: message.content,
      time: new Date().toLocaleTimeString(),
      direction: direction
    });
  }
}
