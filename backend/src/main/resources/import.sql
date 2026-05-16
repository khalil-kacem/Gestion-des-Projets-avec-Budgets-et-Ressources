-- Employés
INSERT INTO employe (id, nom, email, role, equipe) VALUES (1, 'Alice Martin', 'alice@company.com', 'CHEF_PROJET', 'Direction');
INSERT INTO employe (id, nom, email, role, equipe) VALUES (2, 'Bob Dupont', 'bob@company.com', 'DEVELOPPEUR', 'Tech');
INSERT INTO employe (id, nom, email, role, equipe) VALUES (3, 'Charlie Bernard', 'charlie@company.com', 'DESIGNER', 'Creative');
INSERT INTO employe (id, nom, email, role, equipe) VALUES (4, 'Diana Petit', 'diana@company.com', 'TESTEUR', 'QA');
INSERT INTO employe (id, nom, email, role, equipe) VALUES (5, 'Evan Moreau', 'evan@company.com', 'ANALYSTE', 'Data');

-- Ressources (NO FINANCIER - only MATERIEL, LOGICIEL, HUMAIN)
INSERT INTO ressource (id, nom, type, cout, disponibilite) VALUES (1, 'Serveur AWS', 'MATERIEL', 500.0, 'DISPONIBLE');
INSERT INTO ressource (id, nom, type, cout, disponibilite) VALUES (2, 'Licence IntelliJ', 'LOGICIEL', 150.0, 'DISPONIBLE');
INSERT INTO ressource (id, nom, type, cout, disponibilite) VALUES (3, 'Consultant Externe', 'HUMAIN', 2000.0, 'OCCUPEE');
INSERT INTO ressource (id, nom, type, cout, disponibilite) VALUES (4, 'MacBook Pro', 'MATERIEL', 2500.0, 'DISPONIBLE');
INSERT INTO ressource (id, nom, type, cout, disponibilite) VALUES (5, 'Bureau Standing', 'MATERIEL', 800.0, 'DISPONIBLE');

-- Projets
INSERT INTO projet (id, nom, date_debut, date_fin, budget, statut) VALUES (1, 'Refonte Site Web', '2024-01-15', '2024-06-30', 50000.0, 'EN_COURS');
INSERT INTO projet (id, nom, date_debut, date_fin, budget, statut) VALUES (2, 'Application Mobile', '2024-03-01', '2024-09-15', 75000.0, 'EN_COURS');
INSERT INTO projet (id, nom, date_debut, date_fin, budget, statut) VALUES (3, 'Migration Cloud', '2024-02-01', '2024-08-31', 100000.0, 'EN_ATTENTE');

-- Tâches
INSERT INTO tache (id, description, etat, priorite, deadline, projet_id, responsable_id) VALUES (1, 'Analyse des besoins', 'TERMINEE', 'HAUTE', '2024-02-15', 1, 5);
INSERT INTO tache (id, description, etat, priorite, deadline, projet_id, responsable_id) VALUES (2, 'Design UI/UX', 'EN_COURS', 'HAUTE', '2024-03-30', 1, 3);
INSERT INTO tache (id, description, etat, priorite, deadline, projet_id, responsable_id) VALUES (3, 'Développement Backend', 'EN_COURS', 'CRITIQUE', '2024-05-15', 1, 2);
INSERT INTO tache (id, description, etat, priorite, deadline, projet_id, responsable_id) VALUES (4, 'Tests unitaires', 'A_FAIRE', 'MOYENNE', '2024-06-15', 1, 4);
INSERT INTO tache (id, description, etat, priorite, deadline, projet_id, responsable_id) VALUES (5, 'Architecture mobile', 'EN_COURS', 'HAUTE', '2024-04-15', 2, 1);

-- Associations Projet-Ressource
INSERT INTO projet_ressource (projet_id, ressource_id) VALUES (1, 1);
INSERT INTO projet_ressource (projet_id, ressource_id) VALUES (1, 2);
INSERT INTO projet_ressource (projet_id, ressource_id) VALUES (2, 5);
INSERT INTO projet_ressource (projet_id, ressource_id) VALUES (3, 4);

-- Associations Tâche-Ressource
INSERT INTO tache_ressource (tache_id, ressource_id) VALUES (1, 3);
INSERT INTO tache_ressource (tache_id, ressource_id) VALUES (2, 2);
INSERT INTO tache_ressource (tache_id, ressource_id) VALUES (3, 1);
INSERT INTO tache_ressource (tache_id, ressource_id) VALUES (5, 5);