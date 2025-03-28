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

  connectSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = () => { }; // Disable debug messages
    this.stompClient.connect({}, () => {
        console.log("Connected as " + this.userName);
        this.stompClient.subscribe(`/queue/whatsapp` , message => {
          console.log(message)
          console.log(JSON.parse(message.body))
        });

        this.stompClient.subscribe(`/topic/public`,  message => {
          console.log(message)
          console.log(JSON.parse(message.body))
        });

        this.stompClient.send('/app/addUser', { },
          JSON.stringify({ senderName: this.userName, status : 'SENT' }));
      }, (error) => {
         console.log(error);
      });
    };
}
