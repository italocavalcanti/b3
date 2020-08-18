import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-user-exclude',
  templateUrl: './user-exclude.component.html',
  styleUrls: ['./user-exclude.component.css']
})
export class UserExcludeComponent implements OnInit {
  userRemoveForm: FormGroup;
  isLoadingResults = false;

  constructor(private router: Router, private api: ApiService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.userRemoveForm = this.formBuilder.group({
      'id': [null, Validators.required],
    });
  }

  removeUser(form: NgForm) {
    let id =  this.userRemoveForm.value.id;
    if(id == null){
     alert("id inválido");
      return false;
    }
    this.isLoadingResults = true;
    this.api.deleteUser(id)
      .subscribe(res => {
        this.isLoadingResults = false;
        alert("Usuário excluído");
        this.userRemoveForm.reset();
      }, (err) => {
       alert("Usuário não encontrado");
        this.isLoadingResults = false
      });
  }

}
