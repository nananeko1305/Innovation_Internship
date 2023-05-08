import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { LoginComponentComponent } from './components/login-component/login-component.component';
import { RegistrationComponentComponent } from './components/registration-component/registration-component.component';
import { InnovationAddComponent } from './components/Innovation/innovation-add/innovation-add.component';
import { HeaderComponent } from './components/header/header.component';
import { RegistrationComponent } from './components/registration/registration.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginComponentComponent,
    RegistrationComponentComponent,
    InnovationAddComponent,
    HeaderComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
