import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AuthenticationServices } from "src/app/authentication/authentication.services";
import { Task } from '../Task';
import { TaskService } from '../task.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css',
    "../../assets/css/bootstrap.min.css",
    "../../assets/css/Login-Form-Basic.css",
    "../../assets/css/Lora.css",
    "../../assets/css/Nunito Sans.css",
    "../../assets/css/Rosario.css",
    "../../assets/css/style.css",
    "../../assets/css/styles.css",


  ]
})
export class ProfileComponent implements OnInit {

  tasks: Task[];
  taskUpdate: Task = new Task();
  key: string;
  mode: boolean = false;


  public get do(): number {
    return this.tasks
      .filter(task => (task.status == true)).length
  }

  showTaskForm(): void {
    this.mode = true;
  }

  hiddenTaskForm(): void {
    this.mode = false;
    this.taskUpdate = new Task();
    this.getAllTasks();
  }

  constructor(private taskService: TaskService, private router: Router, private authenticationService: AuthenticationServices) {
  }

  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      this.getAllTasks();
      this.taskUpdate.username = localStorage.getItem('username');
    } else {
      // console.log('Login svp');
      this.goLogin();
    }
  }

  hasRole(role: string) {
    return this.authenticationService.hasRole(role);
  }

  getAllTasks(): void {
    this.taskService.getAllTasks()
      .subscribe(datas => {
        this.tasks = datas;
        // console.log('Tâches récuperées...');
        // console.table(datas);
      }, error => {
        console.log(error);
      })
  }

  //Se connecter
  goLogin() {
    let link = '/login';
    this.router.navigate([link]);
  }

  //Se déconnecter
  logout(): void {
    this.authenticationService.logout();
    this.goLogin();
    this.tasks = [];
  }

  //deleteTaskById
  deleteTaskById(id: number) {
    this.taskService.deleteTaskById(id)
      .subscribe(() => {
        // console.log('Tâche supprimée...')
        this.getAllTasks();
      }, error => console.log(error))
  }

  //addTask
  addTask(): void {
    this.taskService.addTask(this.taskUpdate)
      .subscribe(datas => {
        this.hiddenTaskForm();
        // console.log(datas)
        this.getAllTasks();
      }, error => console.log(error))
  }


  editTask(task: Task): void {
    this.taskUpdate = task;
    this.showTaskForm();
  }
  //updateTask
  updateTask() {
    this.addTask()
  }

  //make task
  makeTask(task: Task): void {
    this.taskService.makeTask(task)
      .subscribe(() => {
        // console.log('Task maked')
        this.getAllTasks();
      }, error => console.log(error))
  }

  //search tasks
  search(): void {
    this.taskService.search(this.key)
      .subscribe(result => {
        this.tasks = result;
      }, error => {
        console.log(error);
      })
  }
}
