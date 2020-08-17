import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserFormComponent } from './user-form/user-form.component';
import {RouteServiceService} from "./service/route-service.service";


@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
  providers: [RouteServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
