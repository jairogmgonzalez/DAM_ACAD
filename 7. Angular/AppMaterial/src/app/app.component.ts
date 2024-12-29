import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button'


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    MatSlideToggleModule,
    MatButtonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'AppMaterial';
  modo = 'oscuro';
  isDarkMode = false;
  counter = 0;

  onToggle(): void {
    this.isDarkMode = !this.isDarkMode;
    this.modo = this.modo === 'claro' ? 'oscuro' : 'claro';

    if(this.isDarkMode) {
      document.documentElement.style.backgroundColor = '#333';
    } else {
      document.documentElement.style.backgroundColor = 'white';
    }
  }

  onIncrement(): void {
    this.counter++;
  }

  onDecrement(): void {
    this.counter--;
  }

  onReset(): void {
    this.counter = 0;
  }
}

