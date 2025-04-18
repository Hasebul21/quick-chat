import { Routes } from '@angular/router';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { ChatRoomComponent } from './chat-room/chat-room.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    { path: 'register', component: UserRegistrationComponent },
    { path: 'login', component: UserLoginComponent },
    { path: 'home', component: HomeComponent },
    { path: 'chatroom', component: ChatRoomComponent },
    { path: 'register', component: UserRegistrationComponent },

    // ✅ Redirect empty path to /home
    { path: '', redirectTo: '/home', pathMatch: 'full' }
];
