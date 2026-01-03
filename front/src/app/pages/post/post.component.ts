import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Message } from 'src/app/core/models/message';
import { Post } from 'src/app/core/models/post';
import { MessagesService } from 'src/app/core/services/messages.service';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class PostComponent implements OnInit {
  post: Post | null = null;
  messages: Message[] = [];
  newMessage: string = '';

  constructor(
    private postsService: PostsService,
    private messagesService: MessagesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const postId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadPost(postId);
    this.loadMessages(postId);
  }

  loadPost(postId: number) {
    this.postsService.getPostById(postId).subscribe({
      next: (data) => {
        this.post = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération du post :', err);
      },
    });
  }

  loadMessages(postId: number) {
    this.messagesService.getMessages(postId).subscribe({
      next: (data) => {
        this.messages = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des messages :', err);
      },
    });
  }

  sendMessage() {
    if (this.newMessage.trim() === '') {
      return;
    }

    this.messagesService
      .createMessage(this.post!.id, this.newMessage)
      .subscribe({
        next: (data) => {
          this.messages.push(data);
          this.newMessage = '';
        },
        error: (err) => {
          console.error('Erreur lors de la création du message :', err);
        },
      });
  }
}
