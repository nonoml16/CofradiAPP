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
import { AddHermandadPageComponent } from './ui/add-hermandad-page/add-hermandad-page.component';
import { EditHermandadPageComponent } from './ui/edit-hermandad-page/edit-hermandad-page.component';
import { EditPasoPageComponent } from './ui/edit-paso-page/edit-paso-page.component';
import { AddPasoPageComponent } from './ui/add-paso-page/add-paso-page.component';
import { AddBandaPageComponent } from './ui/add-banda-page/add-banda-page.component';
import { EditBandaPageComponent } from './ui/edit-banda-page/edit-banda-page.component';
import { AddCardPageComponent } from './ui/add-card-page/add-card-page.component';
import { EditCardPageComponent } from './ui/edit-card-page/edit-card-page.component';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  {
    path: 'admin',
    component: SectionComponent,
    children: [
      { path: 'home', component: PageHomeComponent, canActivate: [AuthGuard] },
      {
        path: 'hermandades',
        component: HermandadListPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'hermandades/nueva',
        component: AddHermandadPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'hermandades/editar/:id',
        component: EditHermandadPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'hermandades/editar/:id/nuevo-paso',
        component: AddPasoPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'bandas',
        component: BandasListPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'bandas/nueva',
        component: AddBandaPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'bandas/editar/:id',
        component: EditBandaPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'cards',
        component: CardsListPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'cards/nueva/:id',
        component: AddCardPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'cards/editar/:id',
        component: EditCardPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'users',
        component: UsersListPageComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'paso/editar/:id',
        component: EditPasoPageComponent,
        canActivate: [AuthGuard],
      },
    ],
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '*', component: LoginPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
