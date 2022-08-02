import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationServices } from './authentication/authentication.services';
import { Task } from './Task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  API_URL = 'http://tododaily-env.eba-k5ma8bqh.us-east-2.elasticbeanstalk.com/tasks';
  headers: HttpHeaders = new HttpHeaders()
    .set('Authorization', this.loadToken());
  username: string = this.loadUsername();

  constructor(private http: HttpClient, private authenticationServices: AuthenticationServices) {
  }

  loadToken(): string {
    return localStorage.getItem('jwtToken');
  }

  private loadUsername(): string {
    return localStorage.getItem('username');
  }

  //Test getTasks
  public getAllTasks(): Observable<Task[]> {
    let path: string = 'all';
    if (this.authenticationServices.hasRole("ADMIN")) {
      path = 'alltasks';
    }
    return this.http.get<Task[]>(`${this.API_URL}/${path}`, { 'headers': this.headers });

  }



  //Get task by id
  public getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.API_URL}/${id}`, { 'headers': this.headers });
  }

  //Add task
  public addTask(task: Task): Observable<Task> {
    return this.http.post<Task>(`${this.API_URL}/save`, task, { 'headers': this.headers });
  }

  //Delete task
  public deleteTaskById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/delete/${id}`, { 'headers': this.headers });
  }

  //Update task
  public updateTask(task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.API_URL}/update`, task, { 'headers': this.headers });
  }

  public makeTask(task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.API_URL}/maketask`, task, { 'headers': this.headers })
  }

  public search(key: string): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.API_URL}/search?name=${key}&description=${key}`, { 'headers': this.headers });
  }

}
