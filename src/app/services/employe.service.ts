import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Employe } from '../models/employe.model';
import { ErrorHandlerService } from './error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeService {
  private apiUrl = 'http://localhost:8082/api/employes';

  constructor(
    private http: HttpClient,
    private errorHandler: ErrorHandlerService
  ) {}

  getAll(): Observable<Employe[]> {
    return this.http.get<Employe[]>(this.apiUrl).pipe(
      catchError(this.errorHandler.handleError)
    );
  }

  create(employe: Employe): Observable<Employe> {
    return this.http.post<Employe>(this.apiUrl, employe).pipe(
      catchError(this.errorHandler.handleError)
    );
  }

  update(id: number, employe: Employe): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/${id}`, employe).pipe(
      catchError(this.errorHandler.handleError)
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.errorHandler.handleError)
    );
  }
}