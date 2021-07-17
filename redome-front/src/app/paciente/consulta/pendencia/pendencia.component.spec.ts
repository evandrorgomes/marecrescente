import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendenciaComponent } from './pendencia.component';

describe('PendenciaComponent', () => {
  
  let component: PendenciaComponent;
  let fixture: ComponentFixture<PendenciaComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(PendenciaComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    fixture = undefined;
    component = undefined;
  }) 

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
