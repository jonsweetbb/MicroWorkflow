import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TaskSummary {
  id: string,
  createdOn: Date,
  lastUpdatedOn: Date,
  state: string
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    // 'Connection': 'Keep-Alive',
    'Authorization': 'Basic ' + btoa('12:password')
  })
  
  // , observe: 'body'
  // , params: new HttpParams()
  // , reportProgress: false
  // , responseType: 'json'
  // , withCredentials: true
};

@Injectable({
  providedIn: 'root'
})
export class TaskServiceService {

  // baseUrl: string = 'http://1:password@localhost:8080'
  baseUrl: string = 'http://localhost:8080'

  constructor(private http: HttpClient) { 
  }
  
  getAll(): Observable<TaskSummary[]> {
    return this.http.get<TaskSummary[]>('/task'
    , httpOptions
    );
  }
}
