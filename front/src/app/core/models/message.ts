import { Post } from "./post";
import { User } from "./user";

export interface Message {
  id: number;
  user: User;
  post: Post;
  message: string;
  createdAt: Date;
}