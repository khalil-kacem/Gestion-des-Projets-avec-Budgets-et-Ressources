import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface ProjetSimple {
  id: number;
  nom: string;
  dateDebut: string;
  dateFin: string;
  budget: number;
  statut: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProjetService {
  private apiUrl = 'http://localhost:8082/api/projets';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProjetSimple[]> {
    return this.http.get<ProjetSimple[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getById(id: number): Observable<ProjetSimple> {
    return this.http.get<ProjetSimple>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  create(projet: Omit<ProjetSimple, 'id'>): Observable<ProjetSimple> {
    return this.http.post<ProjetSimple>(this.apiUrl, projet).pipe(
      catchError(this.handleError)
    );
  }

  update(id: number, projet: Omit<ProjetSimple, 'id'>): Observable<ProjetSimple> {
    return this.http.put<ProjetSimple>(`${this.apiUrl}/${id}`, projet).pipe(
      catchError(this.handleError)
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Rapport financier
  getRapport(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/rapport`).pipe(
      catchError(this.handleError)
    );
  }

  // Assigner ressources au projet
  assignerRessources(id: number, ressourceIds: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/ressources`, ressourceIds).pipe(
      catchError(this.handleError)
    );
  }

  // Intercepteur d'erreurs - extrait le message du backend
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Une erreur est survenue';

    if (error.error instanceof ErrorEvent) {
      // Erreur client
      errorMessage = error.error.message;
    } else {
      // Erreur serveur - extrait le message du backend
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else if (error.error && typeof error.error === 'string') {
        errorMessage = error.error;
      } else if (error.status === 400) {
        errorMessage = 'Requete invalide';
      } else if (error.status === 404) {
        errorMessage = 'Projet non trouve';
      } else if (error.status === 500) {
        errorMessage = 'Erreur serveur';
      }
    }

    return throwError(() => new Error(errorMessage));
  }
}