export interface RapportFinancier {
  projetId: number;
  projetNom: string;
  budgetInitial: number;
  coutTotalRessources: number;
  budgetRestant: number;
  depassement: boolean;
}