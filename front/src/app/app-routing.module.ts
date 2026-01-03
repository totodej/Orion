import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { PostsListComponent } from './pages/posts-list/posts-list.component';
import { AuthGuard } from './core/guards/auth.guard';
import { TopicsListComponent } from './pages/topics-list/topics-list.component';
import { MeComponent } from './pages/me/me.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { PostComponent } from './pages/post/post.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'posts-list', component: PostsListComponent, canActivate: [AuthGuard] },
  { path: 'topics-list', component: TopicsListComponent, canActivate: [AuthGuard] },
  { path: 'me', component: MeComponent, canActivate: [AuthGuard] },
  { path: 'create-post', component: CreatePostComponent, canActivate : [AuthGuard] },
  { path: 'post/:id', component: PostComponent, canActivate : [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
