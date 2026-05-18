import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TacheRessourceService } from '../../../services/tache-ressource.service';
import { TacheService } from '../../../services/tache.service';
import { RessourceService } from '../../../services/ressource.service';
import { ToastService } from '../../../services/toast.service';
import { TacheRessource } from '../../../models/tache-ressource.model';
import { Tache } from '../../../models/tache.model';
import { Ressource } from '../../../models/ressource.model';

@Component({
  selector: 'app-tache-ressource-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tache-ressource-list.component.html',
  styleUrls: ['./tache-ressource-list.component.scss']
})
export class TacheRessourceListComponent implements OnInit {
  associations: TacheRessource[] = [];
  taches: Tache[] = [];
  ressources: Ressource[] = [];
  
  selectedTacheId: number | null = null;
  selectedRessourceId: number | null = null;

  constructor(
    private tacheRessourceService: TacheRessourceService,
    private tacheService: TacheService,
    private ressourceService: RessourceService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadAssociations();
    this.loadTaches();
    this.loadRessources();
  }

  loadAssociations(): void {
    this.tacheRessourceService.getAllEnriched().subscribe({
      next: (data) => {
        this.associations = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error('Erreur lors du chargement des associations');
        console.error(err);
      }
    });
  }

  loadTaches(): void {
    this.tacheService.getAll().subscribe({
      next: (data) => {
        this.taches = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  loadRessources(): void {
    this.ressourceService.getAll().subscribe({
      next: (data) => {
        this.ressources = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  createAssociation(): void {
    if (this.selectedTacheId && this.selectedRessourceId) {
      this.tacheRessourceService.assigner(this.selectedTacheId, this.selectedRessourceId).subscribe({
        next: () => {
          this.toastService.success('Association créée avec succès');
          this.loadAssociations();
          this.resetForm();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la création de l\'association');
          console.error(err);
        }
      });
    } else {
      this.toastService.error('Veuillez sélectionner une tâche et une ressource');
    }
  }

  deleteAssociation(tacheId: number, ressourceId: number): void {
    if (confirm('Retirer cette ressource de la tâche ?')) {
      this.tacheRessourceService.retirer(tacheId, ressourceId).subscribe({
        next: () => {
          this.toastService.success('Association supprimée avec succès');
          this.loadAssociations();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la suppression');
          console.error(err);
        }
      });
    }
  }

  resetForm(): void {
    this.selectedTacheId = null;
    this.selectedRessourceId = null;
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
}