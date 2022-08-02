import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationServices} from "src/app/authentication/authentication.services";
import {User} from "src/app/authentication/User";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css',
    "../../../assets/css/bootstrap.min.css",
    "../../../assets/css/Login-Form-Basic.css",
    "../../../assets/css/Lora.css",
    "../../../assets/css/Nunito Sans.css",
    "../../../assets/css/Rosario.css",
    "../../../assets/css/style.css",
    "../../../assets/css/styles.css"
  ]
})


export class RegisterComponent implements OnInit {
  user: User = new User();
  mode: number = 0;
  showSpinner: number = 0;
  constructor(private router: Router, private authenticationServices: AuthenticationServices) {
  }

  ngOnInit(): void {
  }

  signup(): void {
    this.showSpinner = 1;
    this.authenticationServices.signup(this.user)
      .subscribe(response => {
        this.goLogin()
      }, error => {
      this.mode = 1;
      this.showSpinner = 0;
      })
  }

  goProfile() {
    this.router.navigate(['profile'])
  }

  goHome() {
    this.router.navigate(['home']);
  }

  goLogin() {
    this.router.navigate(['login']);
  }


}
