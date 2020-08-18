import { Component, OnInit } from '@angular/core';
import { User } from '../../domain/user';
import { FormBuilder, FormGroup, NgForm, FormControl, Validators } from '@angular/forms';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {
  displayedColumns: string[] = ['id', 'companyId', 'email', 'birthdate','action'];
  dataSource: User[];

  userForm = new FormGroup({
    valueSearch: new FormControl(''),
    selectedSearch: new FormControl(''),
  });

  isLoadingResults = true;
  selectedSearch = "TODOS";
  constructor(private router: Router,private api: ApiService, private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.userForm = this.formBuilder.group({
      'valueSearch': [null, Validators.required],
      'selectSearch': [null, Validators.required]

    });

    this.api.getUsers()
      .subscribe(res => {

        this.dataSource = res;
        this.dataSource.forEach(element => {
          var dats = element.birthdate.toString().split("-");
          var novaDataFormatada = dats[2] + "/" + dats[1] + "/" + dats[0];
          element.birthdate = novaDataFormatada;
        });
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }

  getUser(form: NgForm) {
    this.isLoadingResults = true;

    if (this.selectedSearch == "ID") {
      this.api.getUserById(parseInt(this.userForm.value.valueSearch)).subscribe(res => {
        let aux:  any[] = [];
        aux.push(res);
        this.dataSource = aux;
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });
    };

    if (this.selectedSearch == "IDCOMPANY") {
      this.api.getUsersByCompany(parseInt(this.userForm.value.valueSearch))
        .subscribe(res => {
          console.log(typeof res);
          console.log(typeof this.dataSource);
          this.dataSource = res;

          this.dataSource.forEach(element => {
            var dats = element.birthdate.toString().split("-");
            var novaDataFormatada = dats[2] + "/" + dats[1] + "/" + dats[0];

            element.birthdate = novaDataFormatada;

          });
          this.isLoadingResults = false;
        }, err => {
          console.log(err);
          this.isLoadingResults = false;
        });
    }

    if (this.selectedSearch == "EMAIL") {
      this.api.getUsersByEmail(this.userForm.value.valueSearch)
        .subscribe(res => {
          console.log(typeof res);
          console.log(typeof this.dataSource);
          this.dataSource = res;

          this.dataSource.forEach(element => {
            var dats = element.birthdate.toString().split("-");
            var novaDataFormatada = dats[2] + "/" + dats[1] + "/" + dats[0];

            element.birthdate = novaDataFormatada;

          });
          this.isLoadingResults = false;
        }, err => {
          console.log(err);
          this.isLoadingResults = false;
        });
    }

    if (this.selectedSearch == "TODOS") {
      this.ngOnInit();
    }
  }

  modelChanged() {
    if (this.selectedSearch == "TODOS") {
    }
  }

  deleteUser(id:any){
    this.api.deleteUser(id)
    .subscribe(res => {
     this.ngOnInit();
    }, err => {
      this.isLoadingResults = false;
    });
  }
}
