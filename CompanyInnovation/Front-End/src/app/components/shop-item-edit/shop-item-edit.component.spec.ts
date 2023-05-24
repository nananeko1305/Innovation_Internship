import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopItemEditComponent } from './shop-item-edit.component';

describe('ShopItemEditComponent', () => {
  let component: ShopItemEditComponent;
  let fixture: ComponentFixture<ShopItemEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShopItemEditComponent]
    });
    fixture = TestBed.createComponent(ShopItemEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
