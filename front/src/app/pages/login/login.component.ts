import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/loginRequest';
import { User } from 'src/app/core/models/user';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    const loginRequest = this.form.value as LoginRequest;
    console.log(loginRequest)
    this.authService.login(loginRequest).subscribe({
      next: (user: User) => {
        console.log('User logged in:', user);
        this.router.navigate(['/posts-list']);
      },
      error: (err: any) => {
        console.error('Login error:', err);
      }
    });
  }
}
