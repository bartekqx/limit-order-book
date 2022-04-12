import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavBarFixedFrontComponent } from './nav-bar-fixed-front.component';

describe('NavBarFixedFrontComponent', () => {
  let component: NavBarFixedFrontComponent;
  let fixture: ComponentFixture<NavBarFixedFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavBarFixedFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavBarFixedFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
