import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from 'src/app/core/services/subscription.service';
import { TopicsService } from 'src/app/core/services/topics.service';

@Component({
  selector: 'app-topics-list',
  templateUrl: './topics-list.component.html',
  styleUrls: ['./topics-list.component.scss']
})
export class TopicsListComponent implements OnInit {
  topics: any[] = [];
  subscribedTopics: number[] = [];

  constructor(
    private topicsService: TopicsService,
    private subscriptionService: SubscriptionService,
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.topicsService.getAllTopics().subscribe({
      next: (topics) => {
        console.log("Topics => ", topics);
        this.topics = topics;
      },
      error: (err) => {
        console.error("Error fetching topics => ", err);    
      }
    });

    this.subscriptionService.getMySubscriptions().subscribe({
      next: (subscriptions) => {
        console.log("Subscriptions => ", subscriptions);
        this.subscribedTopics = subscriptions.map(s => s.topicId);
      },
      error: (err) => {
        console.error("Error fetching subscriptions => ", err);    
      }
    });

  }

  isSubscribed(topicId: number): boolean {
    return this.subscribedTopics.includes(topicId);
  }


  subscribe(topicId: number) {
    if (this.isSubscribed(topicId)) return;

    this.subscriptionService.subscribe(topicId).subscribe({
      next: () => {
        this.subscribedTopics.push(topicId);
      },
      error: (err) => {
        console.error(`Error subscribing to topic ${topicId} => `, err);
      }
    });
  }

}
