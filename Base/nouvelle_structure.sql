-- ==========================================================
-- NOUVELLE STRUCTURE DE BASE DE DONNÉES - TAXI BROUSSE
-- ==========================================================
-- Modification principale : 
-- - Le statut des places est maintenant dans place_voyage (par voyage)
-- - et non plus dans place (global au véhicule)
-- - Ainsi un même véhicule peut avoir des réservations différentes
--   selon les dates/heures de voyage
-- ==========================================================

-- Suppression de la base existante et recréation
\c postgres
DROP DATABASE IF EXISTS taxi_brousse;
CREATE DATABASE taxi_brousse;
\c taxi_brousse

-- =========================
-- TABLE : ROLE
-- =========================
CREATE TABLE Role(
   Id_Role SERIAL,
   nom_role VARCHAR(100),
   PRIMARY KEY(Id_Role)
);

-- =========================
-- TABLE : UTILISATEUR
-- =========================
CREATE TABLE utilisateur(
   Id_utilisateur SERIAL,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   telephone VARCHAR(50),
   email VARCHAR(50),
   adresse VARCHAR(50),
   statut VARCHAR(50),
   mot_de_passe VARCHAR(50),
   Id_Role INTEGER NOT NULL,
   PRIMARY KEY(Id_utilisateur),
   FOREIGN KEY(Id_Role) REFERENCES Role(Id_Role)
);

-- =========================
-- TABLE : CHAUFFEUR
-- =========================
CREATE TABLE chauffeur(
   Id_chauffeur SERIAL,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   telephone VARCHAR(50),
   status VARCHAR(50),
   PRIMARY KEY(Id_chauffeur)
);

-- =========================
-- TABLE : TRAJET
-- =========================
CREATE TABLE trajet(
   Id_trajet SERIAL,
   ville_depart VARCHAR(50),
   ville_arrivee VARCHAR(50),
   distance NUMERIC(15,2),
   duree_estimee INTEGER,
   PRIMARY KEY(Id_trajet)
);

-- =========================
-- TABLE : ETAT_VOYAGE
-- =========================
CREATE TABLE etat_voyage(
   Id_etat_voyage SERIAL,
   etat VARCHAR(50),
   PRIMARY KEY(Id_etat_voyage)
);

-- =========================
-- TABLE : TYPE_PAIEMENT
-- =========================
CREATE TABLE type_paiement(
   Id_type_paiement SERIAL,
   type VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id_type_paiement),
   UNIQUE(type)
);

-- =========================
-- TABLE : ETAT_VEHICULE
-- =========================
CREATE TABLE etat_vehicule(
   Id_etat_vehicule SERIAL,
   status VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id_etat_vehicule),
   UNIQUE(status)
);

-- =========================
-- TABLE : ETAT_PAIEMENT
-- =========================
CREATE TABLE etat_paiement(
   Id_etat_paiement SERIAL,
   etat VARCHAR(50),
   PRIMARY KEY(Id_etat_paiement)
);

-- =========================
-- TABLE : TYPE_PLACE
-- =========================
CREATE TABLE type_place(
   Id_type_place SERIAL,
   type VARCHAR(50),
   PRIMARY KEY(Id_type_place)
);

-- =========================
-- TABLE : SOCIETE
-- =========================
CREATE TABLE Societe(
   Id_Societe SERIAL,
   nom VARCHAR(50),
   adresse VARCHAR(50),
   contact VARCHAR(50),
   PRIMARY KEY(Id_Societe)
);

-- =========================
-- TABLE : FACTURE_SOCIETE (NOUVELLE TABLE)
-- =========================
-- Les paiements sont faits sur cette facture par société
-- et répartis proportionnellement sur les prestations
-- =========================
CREATE TABLE facture_societe(
   Id_facture_societe SERIAL,
   num_facture VARCHAR(50) UNIQUE,
   date_facture DATE,
   Id_Societe INTEGER NOT NULL,
   Id_etat_paiement INTEGER,
   PRIMARY KEY(Id_facture_societe),
   FOREIGN KEY(Id_Societe) REFERENCES Societe(Id_Societe),
   FOREIGN KEY(Id_etat_paiement) REFERENCES etat_paiement(Id_etat_paiement)
);

-- =========================
-- TABLE : TYPE_PRESTATION
-- =========================
CREATE TABLE TypePrestation(
   Id_TypePrestation SERIAL,
   description TEXT,
   nom VARCHAR(50),
   PRIMARY KEY(Id_TypePrestation)
);

-- =========================
-- TABLE : TARIF_PRESTATION
-- =========================
CREATE TABLE TarifPrestation(
   Id_TarifPrestation SERIAL,
   prixUnitaire NUMERIC(15,2),
   dateDebut DATE,
   dateFin DATE,
   Id_TypePrestation INTEGER NOT NULL,
   PRIMARY KEY(Id_TarifPrestation),
   FOREIGN KEY(Id_TypePrestation) REFERENCES TypePrestation(Id_TypePrestation)
);

-- =========================
-- TABLE : VEHICULE
-- =========================
CREATE TABLE vehicule(
   Id_vehicule SERIAL,
   immatriculation VARCHAR(50),
   nombre_places INTEGER,
   nb_place_standards INTEGER,
   nb_place_premium INTEGER,
   Id_etat_vehicule INTEGER NOT NULL,
   PRIMARY KEY(Id_vehicule),
   FOREIGN KEY(Id_etat_vehicule) REFERENCES etat_vehicule(Id_etat_vehicule)
);

-- =========================
-- TABLE : VOYAGE
-- =========================
CREATE TABLE voyage(
   Id_voyage SERIAL,
   date_depart DATE NOT NULL,
   heure_depart TIME NOT NULL,
   valeurMax NUMERIC(15,2),
   Id_etat_voyage INTEGER NOT NULL,
   Id_trajet INTEGER NOT NULL,
   Id_chauffeur INTEGER NOT NULL,
   Id_vehicule INTEGER NOT NULL,
   PRIMARY KEY(Id_voyage),
   FOREIGN KEY(Id_etat_voyage) REFERENCES etat_voyage(Id_etat_voyage),
   FOREIGN KEY(Id_trajet) REFERENCES trajet(Id_trajet),
   FOREIGN KEY(Id_chauffeur) REFERENCES chauffeur(Id_chauffeur),
   FOREIGN KEY(Id_vehicule) REFERENCES vehicule(Id_vehicule)
);

-- =========================
-- TABLE : PLACE
-- (Place physique liée au véhicule - SANS statut)
-- Le statut est maintenant dans place_voyage
-- =========================
CREATE TABLE place(
   Id_place SERIAL,
   numero_place VARCHAR(50) NOT NULL,
   Id_type_place INTEGER NOT NULL,
   Id_vehicule INTEGER NOT NULL,
   PRIMARY KEY(Id_place),
   FOREIGN KEY(Id_type_place) REFERENCES type_place(Id_type_place),
   FOREIGN KEY(Id_vehicule) REFERENCES vehicule(Id_vehicule)
);

-- ==========================================================
-- TABLE : PLACE_VOYAGE (NOUVELLE TABLE CLÉ)
-- ==========================================================
-- Cette table lie une place physique à un voyage spécifique
-- avec son propre statut (LIBRE, RESERVEE, OCCUPEE)
-- 
-- AVANTAGE: La même place physique peut être:
--   - LIBRE pour le voyage du 21 janv 10h
--   - RESERVEE pour le voyage du 21 janv 15h
-- ==========================================================
CREATE TABLE place_voyage(
   Id_place_voyage SERIAL,
   statut VARCHAR(50) DEFAULT 'LIBRE',
   Id_place INTEGER NOT NULL,
   Id_voyage INTEGER NOT NULL,
   PRIMARY KEY(Id_place_voyage),
   FOREIGN KEY(Id_place) REFERENCES place(Id_place),
   FOREIGN KEY(Id_voyage) REFERENCES voyage(Id_voyage),
   UNIQUE(Id_place, Id_voyage)  -- Une place ne peut apparaître qu'une fois par voyage
);

-- =========================
-- TABLE : CATEGORIE_PERSONNE
-- =========================
CREATE TABLE categorie_personne(
   Id_categorie_personne SERIAL,
   categorie VARCHAR(50),
   reduction_pourcentage NUMERIC(5,2) DEFAULT 0,
   reduction_fixe NUMERIC(15,2) DEFAULT 0,
   Id_type_place INTEGER,
   PRIMARY KEY(Id_categorie_personne),
   FOREIGN KEY(Id_type_place) REFERENCES type_place(Id_type_place)
);

-- =========================
-- TABLE : PRIX_BILLET
-- =========================
CREATE TABLE prix_billet(
   Id_prix_billet SERIAL,
   prix NUMERIC(15,2) NOT NULL,
   date_debut DATE NOT NULL,
   date_fin DATE,
   Id_type_place INTEGER NOT NULL,
   Id_trajet INTEGER NOT NULL,
   PRIMARY KEY(Id_prix_billet),
   FOREIGN KEY(Id_type_place) REFERENCES type_place(Id_type_place),
   FOREIGN KEY(Id_trajet) REFERENCES trajet(Id_trajet)
);

-- =========================
-- TABLE : FACTURE
-- =========================
CREATE TABLE facture(
   Id_facture SERIAL,
   numFacture VARCHAR(50),
   date_ VARCHAR(50),
   Id_etat_paiement INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_facture),
   FOREIGN KEY(Id_etat_paiement) REFERENCES etat_paiement(Id_etat_paiement),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur)
);

-- =========================
-- TABLE : PAIEMENT
-- =========================
CREATE TABLE paiement(
   Id_paiement SERIAL,
   status VARCHAR(50) NOT NULL,
   Id_facture INTEGER NOT NULL,
   PRIMARY KEY(Id_paiement),
   FOREIGN KEY(Id_facture) REFERENCES facture(Id_facture)
);

-- =========================
-- TABLE : PLUSIEUR_PAIEMENT
-- =========================
CREATE TABLE plusieur_paiement(
   Id_plusieur_paiement SERIAL,
   date_paiement TIMESTAMP NOT NULL,
   montant NUMERIC(15,2),
   Id_paiement INTEGER NOT NULL,
   Id_type_paiement INTEGER NOT NULL,
   PRIMARY KEY(Id_plusieur_paiement),
   FOREIGN KEY(Id_paiement) REFERENCES paiement(Id_paiement),
   FOREIGN KEY(Id_type_paiement) REFERENCES type_paiement(Id_type_paiement)
);

-- =========================
-- TABLE : PRESTATION
-- (Modifiée pour référencer facture_societe)
-- =========================
CREATE TABLE Prestation(
   Id_Prestation SERIAL,
   datePrestation DATE,
   quantite INTEGER,
   Id_voyage INTEGER NOT NULL,
   Id_Societe INTEGER NOT NULL,
   Id_TarifPrestation INTEGER NOT NULL,
   Id_etat_paiement INTEGER,
   Id_facture_societe INTEGER,
   PRIMARY KEY(Id_Prestation),
   FOREIGN KEY(Id_voyage) REFERENCES voyage(Id_voyage),
   FOREIGN KEY(Id_Societe) REFERENCES Societe(Id_Societe),
   FOREIGN KEY(Id_TarifPrestation) REFERENCES TarifPrestation(Id_TarifPrestation),
   FOREIGN KEY(Id_etat_paiement) REFERENCES etat_paiement(Id_etat_paiement),
   FOREIGN KEY(Id_facture_societe) REFERENCES facture_societe(Id_facture_societe)
);

-- =========================
-- TABLE : PAIEMENT_FACTURE_SOCIETE (NOUVELLE TABLE)
-- =========================
-- Les paiements effectués sur une facture société
-- Le montant est réparti proportionnellement sur les prestations
-- =========================
CREATE TABLE paiement_facture_societe(
   Id_paiement_facture_societe SERIAL,
   montant NUMERIC(15,2) NOT NULL,
   date_paiement DATE NOT NULL,
   mode_paiement VARCHAR(50),
   reference VARCHAR(100),
   Id_facture_societe INTEGER NOT NULL,
   PRIMARY KEY(Id_paiement_facture_societe),
   FOREIGN KEY(Id_facture_societe) REFERENCES facture_societe(Id_facture_societe)
);

-- =========================
-- TABLE : RESERVATION
-- =========================
CREATE TABLE reservation(
   Id_reservation SERIAL,
   date_reservation TIMESTAMP,
   statut VARCHAR(50),
   au_nom_de VARCHAR(100),
   Id_facture INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   Id_voyage INTEGER NOT NULL,
   PRIMARY KEY(Id_reservation),
   FOREIGN KEY(Id_facture) REFERENCES facture(Id_facture),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur),
   FOREIGN KEY(Id_voyage) REFERENCES voyage(Id_voyage)
);

-- =========================
-- TABLE : NBR_PLACE_RESERVATION
-- =========================
CREATE TABLE nbr_place_reservation(
   Id_nbr_place_reservation SERIAL,
   nbr_place INTEGER NOT NULL,
   Id_reservation INTEGER NOT NULL,
   Id_categorie_personne INTEGER NOT NULL,
   PRIMARY KEY(Id_nbr_place_reservation),
   FOREIGN KEY(Id_reservation) REFERENCES reservation(Id_reservation),
   FOREIGN KEY(Id_categorie_personne) REFERENCES categorie_personne(Id_categorie_personne)
);

-- ==========================================================
-- TABLE : BILLET
-- MODIFICATION: Référence maintenant PLACE_VOYAGE au lieu de PLACE
-- ==========================================================
CREATE TABLE billet(
   Id_billet SERIAL,
   numero_billet VARCHAR(50) NOT NULL,
   Id_place_voyage INTEGER NOT NULL,
   Id_reservation INTEGER,
   PRIMARY KEY(Id_billet),
   UNIQUE(numero_billet),
   FOREIGN KEY(Id_place_voyage) REFERENCES place_voyage(Id_place_voyage),
   FOREIGN KEY(Id_reservation) REFERENCES reservation(Id_reservation)
);

-- ==========================================================
-- DONNÉES DE BASE
-- ==========================================================

-- Rôles
INSERT INTO Role (nom_role) VALUES ('admin'), ('client');

-- États de voyage
INSERT INTO etat_voyage (etat) VALUES ('en attente'), ('en cours'), ('termine'), ('annule');

-- États de véhicule
INSERT INTO etat_vehicule (status) VALUES ('DISPONIBLE'), ('EN_ROUTE'), ('EN_PANNE');

-- États de paiement
INSERT INTO etat_paiement (etat) VALUES ('NON_PAYE'), ('PAYE'), ('PARTIEL');

-- Types de place
INSERT INTO type_place (type) VALUES ('economique'), ('vip'), ('premium');

-- Catégories de personne
INSERT INTO categorie_personne (categorie, reduction_pourcentage, reduction_fixe, Id_type_place) VALUES 
    ('adulte', 0, 0, 1),      -- adulte economique
    ('enfant', 50, 0, 1),     -- enfant economique (50% réduction)
    ('senior', 20, 0, 1),     -- senior economique (20% réduction)
    ('adulte', 0, 0, 2),      -- adulte vip
    ('enfant', 50, 0, 2),     -- enfant vip
    ('senior', 20, 0, 2),     -- senior vip
    ('adulte', 0, 0, 3),      -- adulte premium
    ('enfant', 50, 0, 3),     -- enfant premium
    ('senior', 20, 0, 3);     -- senior premium

-- Types de paiement
INSERT INTO type_paiement (type) VALUES ('ESPECE'), ('MOBILE_MONEY'), ('CARTE');

-- Utilisateur admin par défaut
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, statut, Id_Role) 
VALUES ('Admin', 'System', 'admin@taxi.mg', 'admin123', 'actif', 1);

-- Utilisateur client par défaut
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, statut, Id_Role) 
VALUES ('Client', 'Divers', 'client@taxi.mg', 'client123', 'actif', 2);

-- ==========================================================
-- SCHÉMA RELATIONNEL SIMPLIFIÉ
-- ==========================================================
-- 
-- PAIEMENT PAR FACTURE SOCIÉTÉ (NOUVEAU SYSTÈME)
-- ==========================================================
--
--  SOCIETE ─────────────── FACTURE_SOCIETE ─────────── PAIEMENT_FACTURE_SOCIETE
--      │                         │
--      │                         │
--      ▼                         ▼
--  PRESTATION ◄──────────────────┘
--
-- PRINCIPE:
-- 1. On crée une FACTURE_SOCIETE qui regroupe les PRESTATIONS d'une société
-- 2. Les paiements sont faits sur la FACTURE_SOCIETE (pas sur chaque prestation)
-- 3. Le montant payé est RÉPARTI PROPORTIONNELLEMENT sur chaque prestation
--
-- EXEMPLE:
-- - Vaniala a 3 prestations: 200 Ar, 200 Ar, 600 Ar → Total: 1000 Ar
-- - Paiement de 500 Ar sur la facture
-- - 500/1000 = 50% → Chaque prestation est payée à 50%
-- - Montant payé par prestation: 100 Ar, 100 Ar, 300 Ar = 500 Ar
-- ==========================================================
--
--  VEHICULE ─────────────────── PLACE (sans statut, juste place physique)
--      │                           │
--      │                           │
--      ▼                           ▼
--  VOYAGE ──────────────────── PLACE_VOYAGE (avec statut LIBRE/RESERVEE)
--      │                           │
--      │                           │
--      ▼                           ▼
--  RESERVATION ────────────────── BILLET
--
-- ==========================================================
-- FONCTIONNEMENT:
-- 1. Quand on crée un VOYAGE → on génère les PLACE_VOYAGE 
--    pour toutes les places du véhicule avec statut = 'LIBRE'
-- 2. Quand on fait une RESERVATION → on met à jour le statut 
--    des PLACE_VOYAGE concernées à 'RESERVEE'
-- 3. Le BILLET référence PLACE_VOYAGE (pas PLACE directement)
-- 4. Ainsi, un nouveau voyage avec le même véhicule aura ses
--    propres PLACE_VOYAGE toutes en statut 'LIBRE'
-- ==========================================================


update prestation set id_facture_societe = 1 where id_prestation = 1;
update prestation set id_facture_societe = 2 where id_prestation = 2;
update prestation set id_facture_societe = 3 where id_prestation = 3;
update prestation set id_facture_societe = 4 where id_prestation = 4;
update prestation set id_facture_societe = null where id_prestation = 5;
delete from paiement_facture_societe;
delete from prestation where id_prestation = 5;