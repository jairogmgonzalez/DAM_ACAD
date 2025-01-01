import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MiComponenteDragDropComponent } from './mi-componente-drag-drop.component';

describe('MiComponenteDragDropComponent', () => {
  let component: MiComponenteDragDropComponent;
  let fixture: ComponentFixture<MiComponenteDragDropComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MiComponenteDragDropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
