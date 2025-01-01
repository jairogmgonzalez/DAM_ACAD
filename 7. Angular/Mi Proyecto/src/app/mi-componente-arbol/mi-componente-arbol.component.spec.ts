import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiComponenteArbolComponent } from './mi-componente-arbol.component';

describe('MiComponenteArbolComponent', () => {
  let component: MiComponenteArbolComponent;
  let fixture: ComponentFixture<MiComponenteArbolComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MiComponenteArbolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
