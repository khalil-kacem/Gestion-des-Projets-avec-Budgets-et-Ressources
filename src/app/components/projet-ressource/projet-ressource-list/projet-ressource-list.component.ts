import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjetRessourceService } from '../../../services/projet-ressource.service';
import { ProjetService } from '../../../services/projet.service';
import { RessourceService } from '../../../services/ressource.service';
import { ToastService } from '../../../services/toast.service';
import { ProjetRessource } from '../../../models/projet-ressource.model';
import { Projet } from '../../../models/projet.model';
import { Ressource } from '../../../models/ressource.model';

@Component({
  selector: 'app-projet-ressource-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './projet-ressource-list.component.html',
  styleUrls: ['./projet-ressource-list.component.scss']
})
export class ProjetRessourceListComponent implements OnInit {
  associations: ProjetRessource[] = [];
  projets: Projet[] = [];
  ressources: Ressource[] = [];
  
  selectedProjetId: number | null = null;
  selectedRessourceId: number | null = null;

  constructor(
    private projetRessourceService: ProjetRessourceService,
    private projetService: ProjetService,
    private ressourceService: RessourceService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadAssociations();
    this.loadProjets();
    this.loadRessources();
  }

  loadAssociations(): void {
    this.projetRessourceService.getAllEnriched().subscribe({
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

  loadProjets(): void {
    this.projetService.getAll().subscribe({
      next: (data) => {
        this.projets = data;
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
    if (this.selectedProjetId && this.selectedRessourceId) {
      this.projetRessourceService.assigner(this.selectedProjetId, this.selectedRessourceId).subscribe({
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
      this.toastService.error('Veuillez sélectionner un projet et une ressource');
    }
  }

  deleteAssociation(projetId: number, ressourceId: number): void {
    if (confirm('Retirer cette ressource du projet ?')) {
      this.projetRessourceService.retirer(projetId, ressourceId).subscribe({
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
    this.selectedProjetId = null;
    this.selectedRessourceId = null;
  }
}