import { Topic } from "./topic";
import { User } from "./user";

export interface Post {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
  user: User;
  topic: Topic;
}