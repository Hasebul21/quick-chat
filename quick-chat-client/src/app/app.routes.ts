import { Routes } from '@angular/router';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { UserLoginComponent } from './user-login/user-login.component';

export const routes: Routes = [{ path: 'register', component: UserRegistrationComponent },
    { path: 'login', component: UserLoginComponent },  
    { path: '', redirectTo: '/login', pathMatch: 'full' }]
