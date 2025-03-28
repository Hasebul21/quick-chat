import { Component } from '@angular/core';
import { Client, IFrame, Stomp } from '@stomp/stompjs';
import { UserStatusComponent } from "./user-status/user-status.component";
import { ChatBoxComponent } from "./chat-box/chat-box.component";
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [UserStatusComponent, ChatBoxComponent, FormsModule]
})
export class AppComponent {
  title = 'quick-chat';
  private stompClient: any | undefined;
  userName: string | undefined;
  userEmail: string | undefined;
  activeUsers: any[] = [];

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      console.log("Connected as " + this.userName);

      this.stompClient.send('/app/addUser', { receipt: 'message-receipt' },
        JSON.stringify({ senderName: this.userName, status: 'SENT' }

        ));

      this.stompClient.subscribe(`/topic/public/activeUsers`, response => {
        console.log(JSON.parse(response.body))
        this.activeUsers = JSON.parse(response.body)
      });

    }, (error) => {
      console.log(error);
    });
  };
}
