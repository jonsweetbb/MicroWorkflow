import { Component, OnInit } from '@angular/core';

export interface Task {
  id: string;
}

const TASKS: Task[] =[
  {id: "1"},
  {id: "2"},
  {id: "4"},
];

@Component({
  selector: 'msfw-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  displayedColumns: string[] = ['taskId'];
  tasks = TASKS;

  constructor() { }

  ngOnInit(): void {
  }

}
