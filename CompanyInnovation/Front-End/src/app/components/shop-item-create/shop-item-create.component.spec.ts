import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopItemCreateComponent } from './shop-item-create.component';

describe('ShopItemCreateComponent', () => {
  let component: ShopItemCreateComponent;
  let fixture: ComponentFixture<ShopItemCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShopItemCreateComponent]
    });
    fixture = TestBed.createComponent(ShopItemCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
