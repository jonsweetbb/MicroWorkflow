import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { TaskServiceService, TaskSummary } from '../task-service/task-service.service';
import { Observable, of as observableOf } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

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
  styleUrls: ['./task-list.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class TaskListComponent implements OnInit {
  displayedColumns: string[] = ['taskId', 'createdOn', 'lastUpdatedOn', 'state'];
  tasks: TaskSummary[] = [];
  initialTask$: Observable<TaskSummary[]>;
  taskUpdate$: Observable<TaskSummary>;

  constructor(private taskService: TaskServiceService
    , private cdr: ChangeDetectorRef
    ) { }

  ngOnInit(): void {
    this.initialTask$ = this.taskService.getAll();
    this.initialTask$.pipe(
      map(data => {
        console.log('Loading finished');
        this.subscribeToUpdates();
        return data;
      }),
      catchError((err) => {
        console.log('Error: ' + err);
        return observableOf([]);
      })
    ).subscribe(data => {
      this.tasks = data
      this.cdr.detectChanges();
    });
  }

  subscribeToUpdates(): void {
    this.taskUpdate$ = this.taskService.subscribeToUpdates();
    console.log("Subcribing to: updates");
    this.taskUpdate$
    .subscribe(
      updatedTask => {
      console.log("Update recieved for: " + updatedTask );
      let index = this.tasks.findIndex(item => item.id == updatedTask.id);
      this.tasks[index] = updatedTask;
      this.tasks = this.tasks.slice();
      this.cdr.detectChanges();
    });
 
  }
}
