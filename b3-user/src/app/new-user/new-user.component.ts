import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { FormBuilder, FormGroup, NgForm, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {
  userNewForm: FormGroup;
  companyId: String = '';
  email: String = '';
  birthdate: Date = null;
  isLoadingResults = false;

  constructor(private router: Router, private api: ApiService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.userNewForm = this.formBuilder.group({
      'companyId': [null, Validators.required],
      'email': [null, Validators.required],
      'birthdate': [null, Validators.required]
    });
  }

  addUser(form: NgForm) {
    let email =  this.userNewForm.value.email;
  
    if(!email.includes('@')){
     alert("Email inválido");
      return false;
    }
    this.isLoadingResults = true;
    this.api.addUser(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.userNewForm.reset();
        this.router.navigate(['/UserNew']);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }
}