import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './ui/login-page/login-page.component';
import { PageHomeComponent } from './ui/page-home/page-home.component';
import { AuthGuard } from './services/auth/auth.guard';
import { SectionComponent } from './section/section.component';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'admin', component: SectionComponent, children: [
    { path: 'home', component: PageHomeComponent, canActivate: [AuthGuard] },
  ]},
  
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '*', component: LoginPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }