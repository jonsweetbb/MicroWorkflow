import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TaskListComponent } from '../task-list/task-list.component';


const routes: Routes = [
  {path: 'tasklist', component: TaskListComponent},
  {path: '', component: TaskListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModuleRoutingModule { }
