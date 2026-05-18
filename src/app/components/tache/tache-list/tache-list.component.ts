import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TacheService } from '../../../services/tache.service';
import { EmployeService } from '../../../services/employe.service';
import { ToastService } from '../../../services/toast.service';
import { Tache } from '../../../models/tache.model';
import { Employe } from '../../../models/employe.model';

@Component({
  selector: 'app-tache-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tache-list.component.html',
  styleUrls: ['./tache-list.component.scss']
})
export class TacheListComponent implements OnInit {
  taches: Tache[] = [];
  employes: Employe[] = [];
  
  newTache: Tache = {
    description: '',
    etat: 'A_FAIRE',
    priorite: 'MOYENNE',
    deadline: '',
    projetId: 1,
    responsableId: undefined
  };
  
  editingTache: Tache | null = null;

  // Ordre de priorité pour le tri
  private prioriteOrdre: { [key: string]: number } = {
    'CRITIQUE': 1,
    'HAUTE': 2,
    'MOYENNE': 3,
    'BASSE': 4
  };

  constructor(
    private tacheService: TacheService,
    private employeService: EmployeService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadTaches();
    this.loadEmployes();
  }

  loadTaches(): void {
    this.tacheService.getAll().subscribe({
      next: (data) => {
        this.taches = this.trierParPriorite(data);
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors du chargement des taches');
        console.error(err);
      }
    });
  }

  // Tri les tâches par priorité : CRITIQUE > HAUTE > MOYENNE > BASSE
  trierParPriorite(taches: Tache[]): Tache[] {
    return [...taches].sort((a, b) => {
      const ordreA = this.prioriteOrdre[a.priorite] || 999;
      const ordreB = this.prioriteOrdre[b.priorite] || 999;
      return ordreA - ordreB;
    });
  }

  loadEmployes(): void {
    this.employeService.getAll().subscribe({
      next: (data) => {
        this.employes = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors du chargement des employes');
        console.error(err);
      }
    });
  }

  createTache(): void {
    this.tacheService.create(this.newTache).subscribe({
      next: () => {
        this.toastService.success('Tache creee avec succes');
        this.loadTaches();
        this.resetForm();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors de la creation de la tache');
        console.error(err);
      }
    });
  }

  editTache(tache: Tache): void {
    this.editingTache = { ...tache };
  }

  updateTache(): void {
    if (this.editingTache && this.editingTache.id) {
      this.tacheService.update(this.editingTache.id, this.editingTache).subscribe({
        next: () => {
          this.toastService.success('Tache modifiee avec succes');
          this.loadTaches();
          this.cancelEdit();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors de la modification de la tache');
          console.error(err);
        }
      });
    }
  }

  changerEtat(tache: Tache, nouvelEtat: string): void {
    if (tache.id) {
      this.tacheService.changerEtat(tache.id, nouvelEtat).subscribe({
        next: () => {
          this.toastService.success(`Etat change en ${nouvelEtat}`);
          this.loadTaches();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors du changement d\'etat');
          console.error(err);
        }
      });
    }
  }

  cancelEdit(): void {
    this.editingTache = null;
  }

  deleteTache(id: number): void {
    if (confirm('Supprimer cette tache ?')) {
      this.tacheService.delete(id).subscribe({
        next: () => {
          this.toastService.success('Tache supprimee avec succes');
          this.loadTaches();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors de la suppression de la tache');
          console.error(err);
        }
      });
    }
  }

  resetForm(): void {
    this.newTache = {
      description: '',
      etat: 'A_FAIRE',
      priorite: 'MOYENNE',
      deadline: '',
      projetId: 1,
      responsableId: undefined
    };
  }

  getPrioriteClass(priorite: string): string {
    switch (priorite) {
      case 'CRITIQUE': return 'badge bg-danger';
      case 'HAUTE': return 'badge bg-warning';
      case 'MOYENNE': return 'badge bg-info';
      case 'BASSE': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }

  getEtatClass(etat: string): string {
    switch (etat) {
      case 'TERMINEE': return 'badge bg-success';
      case 'EN_COURS': return 'badge bg-warning';
      case 'BLOQUEE': return 'badge bg-danger';
      case 'A_FAIRE': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }

  getResponsableNom(responsableId: number | undefined): string {
    if (!responsableId) return 'Non assigne';
    const employe = this.employes.find(e => e.id === responsableId);
    return employe ? employe.nom : 'Inconnu';
  }
}