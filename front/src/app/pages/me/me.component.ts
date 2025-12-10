import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UpdateUserRequest } from 'src/app/core/models/updateUserRequest';
import { User } from 'src/app/core/models/user';
import { AuthService } from 'src/app/core/services/auth.service';
import { SubscriptionService } from 'src/app/core/services/subscription.service';
import { TopicsService } from 'src/app/core/services/topics.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  user: User | null = null;
  loading = true;
  error: string | null = null;

  subscriptions: any[] = [];
  topicsMap: Map<number, any> = new Map();

  public form = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private subscriptionService: SubscriptionService,
    private topicsService: TopicsService,
  ) { }

  ngOnInit(): void {
    this.authService.me().subscribe({
      next: (userData: User) => {
        this.user = userData;
        this.loading = false;
        console.log("User => ", this.user)
        this.form.setValue({
          name: this.user.name,
          email: this.user.email,
          password: ''
        });
      },
      error: () => {
        this.error = "Impossible de récupérer vos informations.";
        this.loading = false;
      }
    });

    this.loadData();

  }

  loadData() {
    this.topicsService.getAllTopics().subscribe({
      next: (topics) => {
        console.log("Topics => ", topics);
        topics.forEach(topic => this.topicsMap.set(topic.id, topic));

        this.subscriptionService.getMySubscriptions().subscribe({
          next: (subs) => {
            console.log("Subscriptions => ", subs);
            this.subscriptions = subs.map(subscription => this.topicsMap.get(subscription.topicId));
          },
          error: (err) => {
            console.error("Error fetching subscriptions => ", err);
          }
        });

      },
      error: (err) => {
        console.error("Error fetching topics => ", err);
      }
    });
  }

  onSubmit(): void {
    const updateRequest: UpdateUserRequest = {
      name: this.form.get("name")!.value,
      email: this.form.get("email")!.value,
      password: this.form.get("password")!.value
    };

    this.authService.updateMe(updateRequest).subscribe({
      next: (updatedUser: User) => {
        this.user = updatedUser;
        console.log('User updated:', this.user);
      },
      error: (err: any) => {
        console.error('Update error:', err);
      }
    });
  }

  unsubscribe(topicId: number) {
    this.subscriptionService.unsubscribe(topicId).subscribe({
      next: () => {
        // Supprime le topic de la liste des abonnements côté frontend
        this.subscriptions = this.subscriptions.filter(t => t.id !== topicId);
      },
      error: (err) => {
        console.error(`Error unsubscribing from topic ${topicId} =>`, err);
      }
    });
  }

}
