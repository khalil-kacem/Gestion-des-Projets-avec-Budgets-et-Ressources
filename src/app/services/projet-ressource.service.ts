import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjetRessource } from '../models/projet-ressource.model';

@Injectable({
  providedIn: 'root'
})
export class ProjetRessourceService {
  private apiUrl = 'http://localhost:8082/api/projets';

  constructor(private http: HttpClient) {}

  getAllEnriched(): Observable<ProjetRessource[]> {
    return this.http.get<ProjetRessource[]>(`${this.apiUrl}/all-with-ressources`);
  }

  assigner(projetId: number, ressourceId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${projetId}/ressources`, [ressourceId]);
  }

  retirer(projetId: number, ressourceId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${projetId}/ressources/${ressourceId}`);
  }
}