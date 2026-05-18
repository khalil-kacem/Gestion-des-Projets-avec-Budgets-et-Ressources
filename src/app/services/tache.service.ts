import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tache } from '../models/tache.model';

@Injectable({
  providedIn: 'root'
})
export class TacheService {
  private apiUrl = 'http://localhost:8082/api/taches';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Tache[]> {
    return this.http.get<Tache[]>(this.apiUrl);
  }

  getById(id: number): Observable<Tache> {
    return this.http.get<Tache>(`${this.apiUrl}/${id}`);
  }

  getByProjet(projetId: number): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/projet/${projetId}`);
  }

  create(tache: Tache): Observable<Tache> {
    return this.http.post<Tache>(this.apiUrl, tache);
  }

  update(id: number, tache: Tache): Observable<Tache> {
    return this.http.put<Tache>(`${this.apiUrl}/${id}`, tache);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Changer état de la tâche
  changerEtat(id: number, etat: string): Observable<Tache> {
    return this.http.patch<Tache>(`${this.apiUrl}/${id}/etat/${etat}`, {});
  }

  // Assigner ressources à la tâche
  assignerRessources(id: number, ressourceIds: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/ressources`, ressourceIds);
  }
}