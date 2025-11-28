import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UpdateUserRequest } from 'src/app/core/models/updateUserRequest';
import { User } from 'src/app/core/models/user';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  user: User | null = null;
  loading = true;
  error: string | null = null;

  public form = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder
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

  
}
