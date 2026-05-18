import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TacheRessource } from '../models/tache-ressource.model';

@Injectable({
  providedIn: 'root'
})
export class TacheRessourceService {
  private apiUrl = 'http://localhost:8082/api/taches';

  constructor(private http: HttpClient) {}

  getAllEnriched(): Observable<TacheRessource[]> {
    return this.http.get<TacheRessource[]>(`${this.apiUrl}/all-with-ressources`);
  }

  assigner(tacheId: number, ressourceId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${tacheId}/ressources`, [ressourceId]);
  }

  retirer(tacheId: number, ressourceId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${tacheId}/ressources/${ressourceId}`);
  }
}