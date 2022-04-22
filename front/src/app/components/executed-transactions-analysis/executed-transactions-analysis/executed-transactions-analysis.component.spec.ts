import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExecutedTransactionsAnalysisComponent } from './executed-transactions-analysis.component';

describe('ExecutedTransactionsAnalysisComponent', () => {
  let component: ExecutedTransactionsAnalysisComponent;
  let fixture: ComponentFixture<ExecutedTransactionsAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExecutedTransactionsAnalysisComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExecutedTransactionsAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
