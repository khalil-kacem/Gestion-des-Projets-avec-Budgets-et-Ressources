import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RessourceService } from '../../../services/ressource.service';
import { ToastService } from '../../../services/toast.service';
import { Ressource } from '../../../models/ressource.model';

@Component({
  selector: 'app-ressource-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ressource-list.component.html',
  styleUrls: ['./ressource-list.component.scss']
})
export class RessourceListComponent implements OnInit {
  ressources: Ressource[] = [];
  searchTerm: string = '';
  newRessource: Ressource = {
    nom: '',
    type: 'MATERIEL',
    cout: 0,
    disponibilite: 'DISPONIBLE'
  };
  editingRessource: Ressource | null = null;

  constructor(
    private ressourceService: RessourceService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadRessources();
  }

  loadRessources(): void {
    this.ressourceService.getAll().subscribe({
      next: (data) => {
        this.ressources = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error('Erreur lors du chargement des ressources');
        console.error(err);
      }
    });
  }

  search(): void {
    if (this.searchTerm.trim()) {
      this.ressourceService.search(this.searchTerm).subscribe({
        next: (data) => {
          this.ressources = data;
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la recherche');
          console.error(err);
        }
      });
    } else {
      this.loadRessources();
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.loadRessources();
  }

  createRessource(): void {
    this.ressourceService.create(this.newRessource).subscribe({
      next: () => {
        this.toastService.success('Ressource créée avec succès');
        this.loadRessources();
        this.resetForm();
      },
      error: (err) => {
        this.toastService.error('Erreur lors de la création de la ressource');
        console.error(err);
      }
    });
  }

  editRessource(ressource: Ressource): void {
    this.editingRessource = { ...ressource };
  }

  updateRessource(): void {
    if (this.editingRessource && this.editingRessource.id) {
      this.ressourceService.update(this.editingRessource.id, this.editingRessource).subscribe({
        next: () => {
          this.toastService.success('Ressource modifiée avec succès');
          this.loadRessources();
          this.cancelEdit();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la modification de la ressource');
          console.error(err);
        }
      });
    }
  }

  cancelEdit(): void {
    this.editingRessource = null;
  }

  deleteRessource(id: number): void {
    if (confirm('Supprimer cette ressource ?')) {
      this.ressourceService.delete(id).subscribe({
        next: () => {
          this.toastService.success('Ressource supprimée avec succès');
          this.loadRessources();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la suppression de la ressource');
          console.error(err);
        }
      });
    }
  }

  resetForm(): void {
    this.newRessource = {
      nom: '',
      type: 'MATERIEL',
      cout: 0,
      disponibilite: 'DISPONIBLE'
    };
  }
}