import { Routes } from '@angular/router';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { ChatRoomComponent } from './chat-room/chat-room.component';
import { HomeComponent } from './home/home.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { PostviewComponent } from './postview/postview.component';
import { AuthGuardServiceService } from './auth-guard-service.service';

export const routes: Routes = [
    { path: 'login', component: UserLoginComponent },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuardServiceService] },
    { path: 'chatroom', component: ChatRoomComponent, canActivate: [AuthGuardServiceService] },
    { path: 'register', component: UserRegistrationComponent },
    { path: 'profile', component: UserProfileComponent, canActivate: [AuthGuardServiceService] },
    { path: 'posts', component: PostviewComponent, canActivate: [AuthGuardServiceService] },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', redirectTo: '/home', }
];
