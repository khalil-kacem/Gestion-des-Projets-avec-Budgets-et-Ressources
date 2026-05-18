import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProjetsComponent } from './features/projets/projets.component';
import { TachesComponent } from './features/taches/taches.component';
import { RessourcesComponent } from './features/ressources/ressources.component';
import { EmployesComponent } from './features/employes/employes.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'projets', component: ProjetsComponent },
  { path: 'taches', component: TachesComponent },
  { path: 'ressources', component: RessourcesComponent },
  { path: 'employes', component: EmployesComponent },
];
