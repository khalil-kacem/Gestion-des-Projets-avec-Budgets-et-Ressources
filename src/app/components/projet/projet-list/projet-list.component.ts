import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjetService, ProjetSimple } from '../../../services/projet.service';
import { RessourceService } from '../../../services/ressource.service';
import { ToastService } from '../../../services/toast.service';
import { Ressource } from '../../../models/ressource.model';

@Component({
  selector: 'app-projet-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './projet-list.component.html',
  styleUrls: ['./projet-list.component.scss']
})
export class ProjetListComponent implements OnInit {
  projets: ProjetSimple[] = [];
  ressources: Ressource[] = [];
  
  newProjet: Omit<ProjetSimple, 'id'> = {
    nom: '',
    dateDebut: '',
    dateFin: '',
    budget: 0,
    statut: 'EN_COURS'
  };
  
  editingProjet: ProjetSimple | null = null;
  selectedRapport: any = null;
  selectedProjetForRessources: ProjetSimple | null = null;
  selectedRessourceIds: number[] = [];

  constructor(
    private projetService: ProjetService,
    private ressourceService: RessourceService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadProjets();
    this.loadRessources();
  }

  loadProjets(): void {
    this.projetService.getAll().subscribe({
      next: (data) => {
        this.projets = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors du chargement des projets');
        console.error(err);
      }
    });
  }

  loadRessources(): void {
    this.ressourceService.getAll().subscribe({
      next: (data) => {
        this.ressources = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors du chargement des ressources');
        console.error(err);
      }
    });
  }

  // Affiche seulement les ressources DISPONIBLES
  ressourcesDisponibles(): Ressource[] {
    return this.ressources.filter(r => r.disponibilite === 'DISPONIBLE');
  }

  createProjet(): void {
    this.projetService.create(this.newProjet).subscribe({
      next: () => {
        this.toastService.success('Projet cree avec succes');
        this.loadProjets();
        this.resetForm();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors de la creation du projet');
        console.error(err);
      }
    });
  }

  editProjet(projet: ProjetSimple): void {
    this.editingProjet = { ...projet };
    this.selectedRapport = null;
    this.selectedProjetForRessources = null;
  }

  updateProjet(): void {
    if (this.editingProjet) {
      const { id, ...projetData } = this.editingProjet;
      this.projetService.update(id, projetData).subscribe({
        next: () => {
          this.toastService.success('Projet modifie avec succes');
          this.loadProjets();
          this.cancelEdit();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors de la modification du projet');
          console.error(err);
        }
      });
    }
  }

  cancelEdit(): void {
    this.editingProjet = null;
  }

  deleteProjet(id: number): void {
    if (confirm('Supprimer ce projet ?')) {
      this.projetService.delete(id).subscribe({
        next: () => {
          this.toastService.success('Projet supprime avec succes');
          this.loadProjets();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors de la suppression du projet');
          console.error(err);
        }
      });
    }
  }

  voirRapport(projet: ProjetSimple): void {
    this.projetService.getRapport(projet.id).subscribe({
      next: (rapport) => {
        this.selectedRapport = rapport;
        this.selectedProjetForRessources = null;
        this.editingProjet = null;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error(err.message || 'Erreur lors du chargement du rapport');
        console.error(err);
      }
    });
  }

  fermerRapport(): void {
    this.selectedRapport = null;
  }

  ouvrirAssignation(projet: ProjetSimple): void {
    this.selectedProjetForRessources = projet;
    this.selectedRessourceIds = [];
    this.selectedRapport = null;
    this.editingProjet = null;
    this.cdr.detectChanges();
  }

  toggleRessourceSelection(id: number): void {
    const index = this.selectedRessourceIds.indexOf(id);
    if (index > -1) {
      this.selectedRessourceIds.splice(index, 1);
    } else {
      this.selectedRessourceIds.push(id);
    }
  }

  isRessourceSelected(id: number): boolean {
    return this.selectedRessourceIds.includes(id);
  }

  assignerRessources(): void {
    if (this.selectedProjetForRessources) {
      this.projetService.assignerRessources(this.selectedProjetForRessources.id, this.selectedRessourceIds).subscribe({
        next: () => {
          this.toastService.success('Ressources assignees avec succes');
          this.selectedProjetForRessources = null;
          this.loadProjets();
        },
        error: (err) => {
          this.toastService.error(err.message || 'Erreur lors de l\'assignation des ressources');
          console.error(err);
        }
      });
    }
  }

  annulerAssignation(): void {
    this.selectedProjetForRessources = null;
    this.selectedRessourceIds = [];
  }

  resetForm(): void {
    this.newProjet = {
      nom: '',
      dateDebut: '',
      dateFin: '',
      budget: 0,
      statut: 'EN_COURS'
    };
  }

  getStatutClass(statut: string): string {
    switch (statut) {
      case 'EN_COURS': return 'badge bg-warning';
      case 'TERMINE': return 'badge bg-success';
      case 'EN_ATTENTE': return 'badge bg-info';
      case 'ANNULE': return 'badge bg-danger';
      default: return 'badge bg-secondary';
    }
  }
}