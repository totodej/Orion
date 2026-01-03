import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './components/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { PostsListComponent } from './pages/posts-list/posts-list.component';
import { TopicsListComponent } from './pages/topics-list/topics-list.component';
import { MeComponent } from './pages/me/me.component';
import { JwtInterceptor } from './core/interceptors/jwt.interceptor';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { MatSelectModule } from '@angular/material/select';
import { PostComponent } from './pages/post/post.component';


@NgModule({
  declarations: [AppComponent, HomeComponent, LoginComponent, RegisterComponent, HeaderComponent, PostsListComponent, TopicsListComponent, MeComponent, CreatePostComponent, PostComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    MatSelectModule,
    FormsModule
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },],
  bootstrap: [AppComponent],
})
export class AppModule {}
