import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TaskSummary {
  id: string,
  createdOn: Date,
  lastUpdatedOn: Date,
  state: string
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TaskServiceService {

  constructor(private http: HttpClient) { 
  }
  
  getAll(): Observable<TaskSummary[]> {
    return this.http.get<TaskSummary[]>('/task'
    , httpOptions
    );
  }

  subscribeToUpdates(page?: number, size?: number): Observable<TaskSummary> {
    return new Observable<TaskSummary>((observer) => {
      let url = '/stream/tasks/updates';
      let eventSource = new EventSource(url);
      eventSource.onmessage = (event) => {
        console.debug('Received event: ', event);
        let json = JSON.parse(event.data);
        observer.next({
          id: json['id'], 
          createdOn: json['createdOn'], 
          lastUpdatedOn: json['lastUpdatedOn'], 
          state: json['state']
        });
      };
      eventSource.onerror = (error) => {
        // readyState === 0 (closed) means the remote source closed the connection,
        // so we can safely treat it as a normal situation. Another way 
        // of detecting the end of the stream is to insert a special element
        // in the stream of events, which the client can identify as the last one.
        if(eventSource.readyState === 0) {
          console.log('The stream has been closed by the server.');
          eventSource.close();
          observer.complete();
        } else {
          observer.error('EventSource error: ' + error);
        }
      }
    });
  }

}
