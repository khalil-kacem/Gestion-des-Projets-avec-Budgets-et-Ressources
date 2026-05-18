import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjetService } from '../../services/projet.service';
import { EmployeService } from '../../services/employe.service';
import { TacheService } from '../../services/tache.service';
import { RessourceService } from '../../services/ressource.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats = {
    projets: 0,
    taches: 0,
    ressources: 0,
    employes: 0
  };

  constructor(
    private projetService: ProjetService,
    private tacheService: TacheService,
    private ressourceService: RessourceService,
    private employeService: EmployeService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.projetService.getAll().subscribe(data => {
      this.stats.projets = data.length;
      this.cdr.detectChanges();
    });
    this.tacheService.getAll().subscribe(data => {
      this.stats.taches = data.length;
      this.cdr.detectChanges();
    });
    this.ressourceService.getAll().subscribe(data => {
      this.stats.ressources = data.length;
      this.cdr.detectChanges();
    });
    this.employeService.getAll().subscribe(data => {
      this.stats.employes = data.length;
      this.cdr.detectChanges();
    });
  }
}