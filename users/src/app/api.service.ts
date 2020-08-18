import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { User } from '../domain/user';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = 'http://localhost:8080/api/v1/user';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getUsers (): Observable<User[]> {
    return this.http.get<User[]>(apiUrl)
      .pipe(
        tap(Users => console.log('leu os User1')),
        catchError(this.handleError('getUsers', []))
      );
  }

  getUsersByCompany (idCompany: any): Observable<User[]> {  
    const url = apiUrl+'/idCompany/{idCompany}?idCompany='+idCompany;
    return this.http.get<User[]>(url)
      .pipe(
        tap(Users => console.log('leu os User')),
        catchError(this.handleError('getUsers', []))
      );
  }

  getUsersByEmail(email: String): Observable<User[]> {  
    const url = apiUrl+'/email/{email}?email='+email;
    return this.http.get<User[]>(url)
      .pipe(
        tap(Users => console.log('leu os User')),
        catchError(this.handleError('getUsers', []))
      );
  }

  getUserById(valueSearch: number): Observable<User[]> {
    console.log(valueSearch);
    const url = apiUrl+'/id/{id}?id='+valueSearch;
    console.log(url);
    
    return this.http.get<User[]>(url).pipe(
      tap(Users => console.log('leu os User1')),
      catchError(this.handleError<User[]>('getUser id=${id}'))
    );
  }

  addUser (User): Observable<User> {

    var dats =  User.birthdate.split("-");
    var novaDataFormatada =  dats[2]+"/"+dats[1]+"/"+dats[0];
   
    User.birthdate = novaDataFormatada;
    return this.http.post<User>(apiUrl, User, httpOptions).pipe(
      // tslint:disable-next-line:no-shadowed-variable
      tap((User: User) =>   alert("Usuário Cadastrado")),
      catchError(this.handleError<User>('addUser'))
    );
  }

  updateUser(id, User): Observable<any> {
    const url = '${apiUrl}/${id}';
    return this.http.put(url, User, httpOptions).pipe(
      tap(_ => console.log('atualiza o produco com id=${id}')),
      catchError(this.handleError<any>('updateUser'))
    );
  }

  deleteUser (id): Observable<User> {
    const url = apiUrl+"/"+id;

    console.log(url);

    return this.http.delete<User>(url, httpOptions).pipe(
      tap(u =>   alert("Registro excluído com sucesso")),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  private handleError<T> (operation, result?: T) {
    return (error: any): Observable<T> => {
      if(operation != "getUsers" && error.status== 404){
        throw new Error(error.message);
      }
      return of(result as T);
    };
  }
}