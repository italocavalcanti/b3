import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserExcludeComponent } from './user-exclude.component';

describe('UserExcludeComponent', () => {
  let component: UserExcludeComponent;
  let fixture: ComponentFixture<UserExcludeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserExcludeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserExcludeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
