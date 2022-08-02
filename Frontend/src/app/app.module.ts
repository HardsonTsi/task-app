import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import {HomeComponent} from "src/app/home/home.component";
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AuthenticationModule } from './authentication/authentication.module';
import { ProfileComponent } from './profile/profile.component';
import { TaskService } from './task.service';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProfileComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule, AuthenticationModule, RouterModule, AppRoutingModule, HttpClientModule, FormsModule
  ],
  providers: [TaskService],
  bootstrap: [AppComponent]
})
export class AppModule { }
