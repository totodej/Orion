import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Topic } from 'src/app/core/models/topic';
import { PostsService } from 'src/app/core/services/posts.service';
import { TopicsService } from 'src/app/core/services/topics.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {
  topics: Topic[] = [];

  public form = this.fb.group({
    topic: ['', [Validators.required]],
    title: ['', [Validators.required, Validators.minLength(3)]],
    description: ['', [Validators.required, Validators.minLength(10)]]
  });

  constructor( 
    private fb: FormBuilder,
    private topicsService: TopicsService,
    private postService: PostsService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.topicsService.getAllTopics().subscribe({
      next: (data) => {
        this.topics = data;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des thèmes :", err);    
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const post = {
      topicId: Number(this.form.value.topic),
      title: this.form.value.title,
      description: this.form.value.description
    };

    this.postService.createPost(post).subscribe({
      next: () => {
        this.router.navigate(['/posts-list']);
      },
      error: (err: any) => {
        console.error('Erreur lors de la création de l’article :', err);
      },
    });
  }
}
