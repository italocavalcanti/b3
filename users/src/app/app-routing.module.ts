import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewUserComponent } from './new-user/new-user.component';
import { UserComponent } from './user/user.component';
import { UserExcludeComponent } from './user-exclude/user-exclude.component';


const routes: Routes = [
  { path: "UserNew", component: NewUserComponent},
  { path: "User", component: UserComponent},
  { path: "UserExclude", component: UserExcludeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
