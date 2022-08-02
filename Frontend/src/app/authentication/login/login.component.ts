import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationServices } from "src/app/authentication/authentication.services";
import { User } from "src/app/authentication/User";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css',
    "../../../assets/css/bootstrap.min.css",
    "../../../assets/css/Login-Form-Basic.css",
    "../../../assets/css/Lora.css",
    "../../../assets/css/Nunito Sans.css",
    "../../../assets/css/Rosario.css",
    "../../../assets/css/style.css",
    "../../../assets/css/styles.css"
  ]
})
export class LoginComponent implements OnInit {

  user: User = new User();
  mode: number = 0;
  showSpinner: number = 0;
  constructor(private authenticationServices: AuthenticationServices, private router: Router) {
  }

  ngOnInit(): void {
    if (this.authenticationServices.isAuthenticated()) {
      this.goProfile();
    }
  }

  goHome() {
    this.router.navigate(['home']);
  }

  goProfile() {
    this.router.navigate(['/profile']);
  }

  login(formLoginValue) {
    this.showSpinner = 1;
    this.authenticationServices.login(this.user)
      .subscribe(response => {
        let jwtToken = response.headers.get('authorization');
        let username = response.headers.get('username');
        let roles = response.headers.get('Roles').toString();

        this.authenticationServices.savePrincipal(jwtToken, username, roles);
        // console.log(roles);
        // console.log(typeof roles);
        
        this.showSpinner = 0;
        this.goProfile();
      }, error => {
        this.mode = 1;
        this.showSpinner = 0;
      })
  }

  goSignup() {
    this.router.navigate(['/signup']);
  }


}
