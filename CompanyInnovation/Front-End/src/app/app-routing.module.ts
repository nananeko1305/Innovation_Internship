import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {HomepageComponent} from "./components/homepage/homepage.component";
import {InnovationCreateComponent} from "./components/innovation-create/innovation-create.component";
import {InnovationListComponent} from "./components/innovation-list/innovation-list.component";
import {
  InnovationAcceptDeclineComponent
} from "./components/innovation-accept-decline/innovation-accept-decline.component";
import {
  InnovationDeclineCommentComponent
} from "./components/innovation-decline-comment/innovation-decline-comment.component";
import {ShopComponent} from "./components/shop/shop.component";
import {ShopItemCreateComponent} from "./components/shop-item-create/shop-item-create.component";
import {ShopItemEditComponent} from "./components/shop-item-edit/shop-item-edit.component";

const routes: Routes = [
  { path: '', redirectTo: '/homepage', pathMatch: 'full' },
  { path: 'homepage', component: HomepageComponent },
  { path : 'login', component : LoginComponent },
  { path : 'registration', component : RegistrationComponent },
  { path : 'innovation-create', component : InnovationCreateComponent },
  { path : 'innovation-list', component : InnovationListComponent },
  { path : 'innovationAcceptDecline/:id', component : InnovationAcceptDeclineComponent },
  { path : 'innovationComment/:id', component : InnovationDeclineCommentComponent },
  { path : 'shop', component : ShopComponent },
  { path : 'shop/create-product', component : ShopItemCreateComponent },
  { path : 'shop/edit-product/:id', component : ShopItemEditComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
