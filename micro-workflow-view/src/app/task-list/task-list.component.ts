import { Component, OnInit } from '@angular/core';
import { TaskServiceService, TaskSummary } from '../task-service/task-service.service';
import { Observable } from 'rxjs';

export interface Task {
  id: string;
  createdOn: Date;
  lastUpdatedOn: Date;
  state: string
}

const TASKS: Task[] =[
  {id: "1", createdOn: new Date("2019-01-16"), lastUpdatedOn: new Date("2020-01-16"), state: "ASSIGNED"},
  {id: "2", createdOn: new Date("2019-02-16"), lastUpdatedOn: new Date("2020-01-17"), state: "ASSIGNED"},
  {id: "4", createdOn: new Date("2019-03-16"), lastUpdatedOn: new Date("2020-01-18"), state: "CLOSED"},
];

@Component({
  selector: 'msfw-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  displayedColumns: string[] = ['taskId', 'createdOn', 'lastUpdatedOn', 'state'];
  tasks = TASKS;
  task$: Observable<TaskSummary[]>;

  constructor(private taskService: TaskServiceService) { }

  ngOnInit(): void {
    this.task$ = this.taskService.getAll();
  }

}
