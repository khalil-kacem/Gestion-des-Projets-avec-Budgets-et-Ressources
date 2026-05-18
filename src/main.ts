import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { DashboardComponent } from './app/components/dashboard/dashboard.component';
import { ProjetListComponent } from './app/components/projet/projet-list/projet-list.component';
import { TacheListComponent } from './app/components/tache/tache-list/tache-list.component';
import { RessourceListComponent } from './app/components/ressource/ressource-list/ressource-list.component';
import { EmployeListComponent } from './app/components/employe/employe-list/employe-list.component';
import { ProjetRessourceListComponent } from './app/components/projet-ressource/projet-ressource-list/projet-ressource-list.component';
import { TacheRessourceListComponent } from './app/components/tache-ressource/tache-ressource-list/tache-ressource-list.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter([
      { path: '', component: DashboardComponent },
      { path: 'projets', component: ProjetListComponent },
      { path: 'taches', component: TacheListComponent },
      { path: 'ressources', component: RessourceListComponent },
      { path: 'employes', component: EmployeListComponent },
      { path: 'projet-ressources', component: ProjetRessourceListComponent },
      { path: 'tache-ressources', component: TacheRessourceListComponent }
    ]),
    provideHttpClient(),
    importProvidersFrom(FormsModule)
  ]
});