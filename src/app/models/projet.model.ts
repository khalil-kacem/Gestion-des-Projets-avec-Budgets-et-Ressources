export interface Projet {
  id?: number;
  nom: string;
  dateDebut: string;
  dateFin?: string;
  budget: number;
  statut: string;
  coutTotal?: number;
  budgetRestant?: number;
  nombreTaches?: number;
}