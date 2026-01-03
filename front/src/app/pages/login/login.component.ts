import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/loginRequest';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginError: string | null = null;

  public form = this.fb.group({
    identifier: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.min(3)]],
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.loginError = null;
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.router.navigate(['/posts-list']);
      },
      error: () => {
        this.loginError = 'Identifiant ou mot de passe incorrect';
      },
    });
  }
}
