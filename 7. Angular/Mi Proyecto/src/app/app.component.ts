import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MiComponenteTablaComponent } from './mi-componente-tabla/mi-componente-tabla.component';
import { MiComponenteFormularioComponent } from './mi-componente-formulario/mi-componente-formulario.component';
import { MiComponenteNavegacionComponent } from './mi-componente-navegacion/mi-componente-navegacion.component';
import { MiComponentePanelComponent } from './mi-componente-panel/mi-componente-panel.component';
import { MiComponenteDragDropComponent } from './mi-componente-drag-drop/mi-componente-drag-drop.component';
import { MiComponenteArbolComponent } from './mi-componente-arbol/mi-componente-arbol.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MiComponenteTablaComponent,
    MiComponenteFormularioComponent,
    MiComponenteNavegacionComponent,
    MiComponentePanelComponent,
    MiComponenteDragDropComponent,
    MiComponenteArbolComponent,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'miproyecto';
}
