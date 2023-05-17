import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import {MatRippleModule} from "@angular/material/core";
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import {AngularMaterialModule} from "./angular-material.module";
import {ReactiveFormsModule} from "@angular/forms";
import { InnovationCreateComponent } from './components/innovation-create/innovation-create.component';
import { EngineerViewOfInnovationsComponent } from './components/engineer-view-of-innovations/engineer-view-of-innovations.component';
import { InnovationViewComponent } from './components/innovation-view/innovation-view.component';
import { InnovationListComponent } from './components/innovation-list/innovation-list.component';
import { InnovationAcceptDeclineComponent } from './components/innovation-accept-decline/innovation-accept-decline.component';
import { InnovationDeclineCommentComponent } from './components/innovation-decline-comment/innovation-decline-comment.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptorService } from './auth-interceptor.service';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegistrationComponent,
    HomepageComponent,
    InnovationCreateComponent,
    EngineerViewOfInnovationsComponent,
    InnovationViewComponent,
    InnovationListComponent,
    InnovationAcceptDeclineComponent,
    InnovationDeclineCommentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatRippleModule,
    RouterModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
}],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
