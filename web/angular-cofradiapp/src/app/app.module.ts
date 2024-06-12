import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './ui/login-page/login-page.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { AddHermandadPageComponent } from './ui/add-hermandad-page/add-hermandad-page.component';
import { EditHermandadPageComponent } from './ui/edit-hermandad-page/edit-hermandad-page.component';
import { PasoHermandadItemComponent } from './components/paso-hermandad-item/paso-hermandad-item.component';
import { CardHermandadItemComponent } from './components/card-hermandad-item/card-hermandad-item.component';
import { EditPasoPageComponent } from './ui/edit-paso-page/edit-paso-page.component';
import { BandaPasoItemComponent } from './components/banda-paso-item/banda-paso-item.component';
import { AddPasoPageComponent } from './ui/add-paso-page/add-paso-page.component';

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
    AddHermandadPageComponent,
    EditHermandadPageComponent,
    PasoHermandadItemComponent,
    CardHermandadItemComponent,
    EditPasoPageComponent,
    BandaPasoItemComponent,
    AddPasoPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
