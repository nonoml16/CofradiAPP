import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './ui/login-page/login-page.component';
import { PageHomeComponent } from './ui/page-home/page-home.component';
import { AuthGuard } from './services/auth/auth.guard';
import { SectionComponent } from './section/section.component';
import { HermandadListPageComponent } from './ui/hermandad-list-page/hermandad-list-page.component';
import { BandasListPageComponent } from './ui/bandas-list-page/bandas-list-page.component';
import { CardsListPageComponent } from './ui/cards-list-page/cards-list-page.component';
import { UsersListPageComponent } from './ui/users-list-page/users-list-page.component';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'admin', component: SectionComponent, children: [
    { path: 'home', component: PageHomeComponent, canActivate: [AuthGuard] },
    { path: 'hermandades', component: HermandadListPageComponent, canActivate: [AuthGuard] },
    { path: 'bandas', component: BandasListPageComponent, canActivate: [AuthGuard] },
    { path: 'cards', component: CardsListPageComponent, canActivate: [AuthGuard] },
    { path: 'users', component: UsersListPageComponent, canActivate: [AuthGuard] },
  ]},
  
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '*', component: LoginPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }