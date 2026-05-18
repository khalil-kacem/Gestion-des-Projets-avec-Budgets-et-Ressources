# 📊 Gestion des Projets avec Budgets et Ressources

> Application web fullstack de gestion de projets permettant de piloter des projets, tâches, employés, ressources et budgets, avec génération de rapports financiers et visualisation via un dashboard interactif.

---

## 📋 Table des Matières

- [Description](#-description)
- [Fonctionnalités](#-fonctionnalités)
- [Technologies Utilisées](#-technologies-utilisées)
- [Architecture du Projet](#-architecture-du-projet)
- [Installation et Exécution](#-installation-et-exécution)
  - [Option 1 — Docker Compose (Recommandé)](#option-1--docker-compose-recommandé)
  - [Option 2 — Développement Local (Sans Docker)](#option-2--développement-local-sans-docker)
  - [Option 3 — Déploiement Google Cloud Platform](#option-3--déploiement-google-cloud-platform)
- [API Endpoints](#-api-endpoints)
- [Configuration Docker](#-configuration-docker)
- [Accès aux Services](#-accès-aux-services)

---

## 📌 Description

Cette application web fullstack permet aux équipes de gérer l'ensemble du cycle de vie de leurs projets, depuis la planification jusqu'au suivi financier. Elle offre une interface intuitive pour coordonner les ressources humaines et matérielles, suivre l'avancement des tâches et produire des rapports détaillés.

---

## ✨ Fonctionnalités

- 📁 **Gestion de projets** — création, modification, suivi des statuts et des dates
- ✅ **Suivi des tâches** — association et gestion des tâches par projet
- 👥 **Gestion des employés** — affectation et suivi des intervenants
- 🔧 **Allocation des ressources** — ressources matérielles, humaines et financières
- 📈 **Rapports financiers** — génération de rapports détaillés par projet
- 📊 **Dashboard interactif** — visualisation synthétique des données clés

---

## 🛠️ Technologies Utilisées

### Backend

| Technologie | Version | Rôle |
|---|---|---|
| Java | 17 | Langage de programmation |
| Spring Boot | 3.2.5 | Framework backend |
| Spring Data JPA | 3.2.5 | ORM et accès aux données |
| MySQL Connector | 8.3.0 | Driver JDBC MySQL |
| Swagger / OpenAPI | 2.3.0 | Documentation interactive de l'API |
| Maven | 3.9+ | Gestionnaire de dépendances |

### Frontend

| Technologie | Version | Rôle |
|---|---|---|
| Angular | 17 | Framework frontend SPA |
| TypeScript | 5.3 | Langage frontend |
| Bootstrap | 5.3 | Framework CSS responsive |
| RxJS | 7.8 | Programmation réactive |
| Nginx | Alpine | Serveur web en production |

### DevOps & Déploiement

| Technologie | Rôle |
|---|---|
| Docker | Conteneurisation des services |
| Docker Compose | Orchestration multi-conteneurs |
| Google Cloud Run | Déploiement serverless |
| Google Cloud SQL | Base de données managée |

---

## 🗂️ Architecture du Projet

```
mini_projet_jee/
├── backend/                    # API REST Spring Boot
│   ├── src/main/java/          # Code source Java
│   ├── src/main/resources/     # Fichiers de configuration
│   ├── Dockerfile              # Image Docker backend
│   ├── pom.xml                 # Dépendances Maven
│   └── app.yaml                # Configuration App Engine
│
├── frontend-app/               # Application Angular
│   ├── src/app/                # Composants, services et modules
│   ├── src/environments/       # Configuration des environnements
│   ├── Dockerfile              # Image Docker frontend
│   ├── nginx.conf              # Configuration Nginx
│   └── angular.json            # Configuration Angular CLI
│
└── docker-compose.yml          # Orchestration complète Docker
```

---

## 🚀 Installation et Exécution

### Prérequis Communs

- [Docker Desktop](https://www.docker.com/products/docker-desktop) *(pour Docker)*
- [Node.js](https://nodejs.org/) 20+ *(pour développement local)*
- [Java JDK](https://adoptium.net/) 17+ *(pour développement local)*
- [Maven](https://maven.apache.org/) 3.9+ *(pour développement local)*

---

### Option 1 — Docker Compose (Recommandé)

Lance l'intégralité de l'application (backend + frontend + MySQL) en une seule commande.

```bash
# 1. Cloner le repository
git clone https://github.com/khalil-kacem/Gestion-des-Projets-avec-Budgets-et-Ressources.git
cd mini_projet_jee

# 2. Construire et démarrer tous les services
docker-compose up --build

# Ou en arrière-plan
docker-compose up --build -d
```

**Arrêt et nettoyage :**

```bash
# Arrêter les services
docker-compose down

# Arrêter et supprimer les volumes (réinitialiser la base de données)
docker-compose down -v
```

---

### Option 2 — Développement Local (Sans Docker)

#### 🔹 Base de données MySQL

```bash
docker run -d \
  --name mysql-projet \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=projetdbb \
  -p 3306:3306 \
  mysql:8.0
```

#### 🔹 Backend Spring Boot

```bash
cd backend

# Compiler et lancer
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

> L'API démarre sur **http://localhost:8080**

#### 🔹 Frontend Angular

```bash
cd frontend-app

# Installer les dépendances
npm install

# Lancer en mode développement
ng serve
```

> L'interface démarre sur **http://localhost:4200**

---

### Option 3 — Déploiement Google Cloud Platform

#### Étape 1 — Prérequis GCP

```bash
# Authentification et configuration du projet
gcloud auth login
gcloud config set project VOTRE_PROJECT_ID

# Activation des services nécessaires
gcloud services enable run.googleapis.com sqladmin.googleapis.com
```

#### Étape 2 — Création de la base de données Cloud SQL

```bash
# Créer l'instance MySQL
gcloud sql instances create projet-mysql \
    --database-version=MYSQL_8_0 \
    --tier=db-f1-micro \
    --region=europe-west1 \
    --storage-size=10GB

# Créer la base de données
gcloud sql databases create projetdbb --instance=projet-mysql

# Définir le mot de passe root
gcloud sql users set-password root \
    --host=% \
    --instance=projet-mysql \
    --password=rootpassword
```

#### Étape 3 — Déploiement du Backend (App Engine)

```bash
cd backend

# Configurer app.yaml avec votre instance Cloud SQL, puis déployer
gcloud app deploy
```



#### Étape 4 — Déploiement du Frontend (Cloud Run)

```bash
cd frontend-app

# Construire et pousser l'image Docker
docker build -t gcr.io/VOTRE_PROJECT_ID/frontend:latest .
docker push gcr.io/VOTRE_PROJECT_ID/frontend:latest

# Déployer sur Cloud Run
gcloud run deploy frontend \
    --image gcr.io/VOTRE_PROJECT_ID/frontend:latest \
    --platform managed \
    --region=europe-west1 \
    --allow-unauthenticated \
    --set-env-vars="BACKEND_URL="
```

---

## 🌐 Accès aux Services

| Service | URL | Description |
|---|---|---|
| Frontend | http://localhost:8081 | Interface Angular |
| Backend API | http://localhost:8082/api/projets | API REST |
| Swagger UI | http://localhost:8082/swagger-ui.html | Documentation interactive |
| MySQL | localhost:3307 | Base de données (`root` / `rootpassword`) |

---

## 📡 API Endpoints

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/api/projets` | Lister tous les projets |
| `GET` | `/api/projets/{id}` | Détails d'un projet |
| `POST` | `/api/projets` | Créer un projet |
| `PUT` | `/api/projets/{id}` | Modifier un projet |
| `DELETE` | `/api/projets/{id}` | Supprimer un projet |
| `GET` | `/api/taches` | Lister toutes les tâches |
| `GET` | `/api/employes` | Lister tous les employés |
| `GET` | `/api/ressources` | Lister toutes les ressources |
| `GET` | `/api/projets/{id}/rapport` | Rapport financier d'un projet |

> 📖 Documentation complète disponible sur `/swagger-ui.html`

---

## 🐳 Configuration Docker

### Dockerfile — Backend

```dockerfile
# Étape 1 : Build
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Dockerfile — Frontend

```dockerfile
# Étape 1 : Build Angular
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build -- --configuration production

# Étape 2 : Serveur Nginx
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist/frontend-app/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### docker-compose.yml

```yaml
services:
  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: projetdbb
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  backend:
    build: ./backend
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    ports:
      - "8082:8080"
    depends_on:
      - db

  frontend:
    build: ./frontend-app
    ports:
      - "8081:80"
    depends_on:
      - backend

volumes:
  mysql-data:
```

