import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeService } from '../../../services/employe.service';
import { ToastService } from '../../../services/toast.service';
import { Employe } from '../../../models/employe.model';

@Component({
  selector: 'app-employe-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employe-list.component.html',
  styleUrls: ['./employe-list.component.scss']
})
export class EmployeListComponent implements OnInit {
  employes: Employe[] = [];
  newEmploye: Employe = {
    nom: '',
    email: '',
    role: 'DEVELOPPEUR',
    equipe: ''
  };
  editingEmploye: Employe | null = null;

  constructor(
    private employeService: EmployeService,
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadEmployes();
  }

  loadEmployes(): void {
    this.employeService.getAll().subscribe({
      next: (data) => {
        this.employes = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.error('Erreur lors du chargement des employés');
        console.error(err);
      }
    });
  }

  createEmploye(): void {
    this.employeService.create(this.newEmploye).subscribe({
      next: () => {
        this.toastService.success('Employé créé avec succès');
        this.loadEmployes();
        this.resetForm();
      },
      error: (err) => {
        this.toastService.error('Erreur lors de la création de l\'employé');
        console.error(err);
      }
    });
  }

  editEmploye(employe: Employe): void {
    this.editingEmploye = { ...employe };
  }

  updateEmploye(): void {
    if (this.editingEmploye && this.editingEmploye.id) {
      this.employeService.update(this.editingEmploye.id, this.editingEmploye).subscribe({
        next: () => {
          this.toastService.success('Employé modifié avec succès');
          this.loadEmployes();
          this.cancelEdit();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la modification de l\'employé');
          console.error(err);
        }
      });
    }
  }

  cancelEdit(): void {
    this.editingEmploye = null;
  }

  deleteEmploye(id: number): void {
    if (confirm('Supprimer cet employé ?')) {
      this.employeService.delete(id).subscribe({
        next: () => {
          this.toastService.success('Employé supprimé avec succès');
          this.loadEmployes();
        },
        error: (err) => {
          this.toastService.error('Erreur lors de la suppression de l\'employé');
          console.error(err);
        }
      });
    }
  }

  resetForm(): void {
    this.newEmploye = {
      nom: '',
      email: '',
      role: 'DEVELOPPEUR',
      equipe: ''
    };
  }
}