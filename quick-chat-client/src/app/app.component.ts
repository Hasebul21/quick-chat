import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { Client, IFrame, Stomp } from '@stomp/stompjs';
import { UserStatusComponent } from "./user-status/user-status.component";
import { ChatBoxComponent } from "./chat-box/chat-box.component";
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';
import { CommonModule } from '@angular/common';
import { UserRegistrationComponent } from "./user-registration/user-registration.component";
import { UserLoginComponent } from "./user-login/user-login.component";
import { Router, RouterModule } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthService } from './service/auth-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [MatToolbarModule, FormsModule, CommonModule, RouterModule]
})
export class AppComponent {
  title = 'quick-chat';
  loggedInUser: any = null;
  constructor(private auth: AuthService,
    private toastr: ToastrService,
    private router : Router
  ) {

  }

  ngOnInit() {
    this.loggedInUser = this.auth.getLoggedInUser();
    console.log(this.loggedInUser);
  }

  logout() {
    this.auth.logout(this.loggedInUser.userEmail).subscribe({
      next: (response) => {
        console.log('Logout successful', response);
        this.auth.removeLoggedInUser();
        this.loggedInUser = null;
        this.toastr.success('Logout successfully!', 'Success');
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Logout failed', error);
      }
    });
  }
}
