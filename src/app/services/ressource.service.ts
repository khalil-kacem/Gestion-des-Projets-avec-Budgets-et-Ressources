import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ressource } from '../models/ressource.model';

@Injectable({
  providedIn: 'root'
})
export class RessourceService {
  private apiUrl = 'http://localhost:8082/api/ressources';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Ressource[]> {
    return this.http.get<Ressource[]>(this.apiUrl);
  }

  getById(id: number): Observable<Ressource> {
    return this.http.get<Ressource>(`${this.apiUrl}/${id}`);
  }

  create(ressource: Ressource): Observable<Ressource> {
    return this.http.post<Ressource>(this.apiUrl, ressource);
  }

  update(id: number, ressource: Ressource): Observable<Ressource> {
    return this.http.put<Ressource>(`${this.apiUrl}/${id}`, ressource);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Recherche par nom
  search(nom: string): Observable<Ressource[]> {
    return this.http.get<Ressource[]>(`${this.apiUrl}/search`, {
      params: { nom }
    });
  }
}