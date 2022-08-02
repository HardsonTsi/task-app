import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css',
    "../../assets/css/bootstrap.min.css",
    "../../assets/css/Login-Form-Basic.css",
    "../../assets/css/Lora.css",
    "../../assets/css/Nunito Sans.css",
    "../../assets/css/Rosario.css",
    "../../assets/css/style.css",
    "../../assets/css/styles.css"
  ]
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  register(){
    this.router.navigate(['signup']);
  }


}
