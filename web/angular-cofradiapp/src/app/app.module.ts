import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './ui/login-page/login-page.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { PageHomeComponent } from './ui/page-home/page-home.component';
import { SectionComponent } from './section/section.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HermandadListPageComponent } from './ui/hermandad-list-page/hermandad-list-page.component';
import { BandasListPageComponent } from './ui/bandas-list-page/bandas-list-page.component';
import { CardsListPageComponent } from './ui/cards-list-page/cards-list-page.component';
import { UsersListPageComponent } from './ui/users-list-page/users-list-page.component';
import { HermandadListItemComponent } from './components/hermandad-list-item/hermandad-list-item.component';
import { BandaListItemComponent } from './components/banda-list-item/banda-list-item.component';
import { CardListItemComponent } from './components/card-list-item/card-list-item.component';
import { UserListItemComponent } from './components/user-list-item/user-list-item.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    LoginComponent,
    PageHomeComponent,
    SectionComponent,
    NavbarComponent,
    HermandadListPageComponent,
    BandasListPageComponent,
    CardsListPageComponent,
    UsersListPageComponent,
    HermandadListItemComponent,
    BandaListItemComponent,
    CardListItemComponent,
    UserListItemComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
