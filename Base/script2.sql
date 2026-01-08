CREATE TABLE Role(
   Id_Role SERIAL,
   nom_role VARCHAR(100) ,
   PRIMARY KEY(Id_Role)
);

CREATE TYPE statut_utilisateur AS ENUM ('actif', 'inactif');

CREATE TABLE utilisateur(
   Id_utilisateur SERIAL,
   nom VARCHAR(50) ,
   prenom VARCHAR(50) ,
   telephone VARCHAR(50) ,
   email VARCHAR(50) ,
   adresse VARCHAR(50) ,
   statut statut_utilisateur,
   mot_de_passe VARCHAR(50) ,
   Id_Role INTEGER NOT NULL,
   PRIMARY KEY(Id_utilisateur),
   FOREIGN KEY(Id_Role) REFERENCES Role(Id_Role)
);

CREATE TABLE chauffeur(
   Id_chauffeur SERIAL,
   nom VARCHAR(50) ,
   prenom VARCHAR(50) ,
   telephone VARCHAR(50) ,
   status statut_utilisateur ,
   PRIMARY KEY(Id_chauffeur)
);

CREATE TABLE trajet(
   Id_trajet SERIAL,
   ville_depart VARCHAR(50) ,
   ville_arrivee VARCHAR(50) ,
   distance NUMERIC(15,2)  ,
   duree_estimee INTEGER,
   PRIMARY KEY(Id_trajet)
);

CREATE TABLE etat_voyage(
   Id_etat_voyage SERIAL,
   etat VARCHAR(50) ,
   PRIMARY KEY(Id_etat_voyage)
);

CREATE TABLE type_paiement(
   Id_type_paiement SERIAL,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_type_paiement),
   UNIQUE(type)
);

CREATE TABLE prix_billet(
   Id_prix_billet SERIAL,
   prix NUMERIC(15,2)   NOT NULL,
   date_debut DATE NOT NULL,
   date_fin VARCHAR(50) ,
   Id_trajet INTEGER NOT NULL,
   PRIMARY KEY(Id_prix_billet),
   FOREIGN KEY(Id_trajet) REFERENCES trajet(Id_trajet)
);

CREATE TABLE billet(
   Id_billet SERIAL,
   numero_billet VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_billet),
   UNIQUE(numero_billet)
);

CREATE TABLE etat_vehicule(
   Id_etat_vehicule SERIAL,
   status VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_etat_vehicule),
   UNIQUE(status)
);

CREATE TABLE etat_paiement(
   Id_etat_paiement SERIAL,
   etat VARCHAR(50) ,
   PRIMARY KEY(Id_etat_paiement)
);

CREATE TABLE vehicule(
   Id_vehicule SERIAL,
   immatriculation VARCHAR(50) ,
   nombre_places INTEGER,
   Id_etat_vehicule INTEGER NOT NULL,
   PRIMARY KEY(Id_vehicule),
   FOREIGN KEY(Id_etat_vehicule) REFERENCES etat_vehicule(Id_etat_vehicule)
);

CREATE TABLE voyage(
   Id_voyage SERIAL,
   date_depart DATE NOT NULL,
   heure_depart TIME NOT NULL,
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

CREATE TYPE status_place AS ENUM ('libre', 'reservee', 'occupee');
CREATE TABLE place(
   Id_place SERIAL,
   numero_place VARCHAR(50)  NOT NULL,
   statut status_place,
   Id_vehicule INTEGER NOT NULL,
   PRIMARY KEY(Id_place),
   FOREIGN KEY(Id_vehicule) REFERENCES vehicule(Id_vehicule)
);

CREATE TABLE facture(
   Id_facture SERIAL,
   numFacture VARCHAR(50) ,
   date_ VARCHAR(50) ,
   Id_etat_paiement INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_facture),
   FOREIGN KEY(Id_etat_paiement) REFERENCES etat_paiement(Id_etat_paiement),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur)
);

CREATE TABLE paiement(
   Id_paiement SERIAL,
   status VARCHAR(50)  NOT NULL,
   Id_facture INTEGER NOT NULL,
   PRIMARY KEY(Id_paiement),
   FOREIGN KEY(Id_facture) REFERENCES facture(Id_facture)
);

CREATE TABLE plusieur_paiement(
   Id_plusieur_paiement SERIAL,
   date_paiement TIMESTAMP NOT NULL,
   montant NUMERIC(15,2)  ,
   Id_paiement INTEGER NOT NULL,
   Id_type_paiement INTEGER NOT NULL,
   PRIMARY KEY(Id_plusieur_paiement),
   FOREIGN KEY(Id_paiement) REFERENCES paiement(Id_paiement),
   FOREIGN KEY(Id_type_paiement) REFERENCES type_paiement(Id_type_paiement)
);

CREATE TYPE status_reservation AS ENUM ('reservee', 'confirmee', 'annulee');
CREATE TABLE reservation(
   Id_reservation SERIAL,
   date_reservation TIMESTAMP,
   statut status_reservation,
   au_nom_de VARCHAR(100) ,
   Id_billet INTEGER NOT NULL,
   Id_facture INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   Id_voyage INTEGER NOT NULL,
   Id_place INTEGER NOT NULL,
   PRIMARY KEY(Id_reservation),
   UNIQUE(Id_billet),
   UNIQUE(Id_place),
   FOREIGN KEY(Id_billet) REFERENCES billet(Id_billet),
   FOREIGN KEY(Id_facture) REFERENCES facture(Id_facture),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur),
   FOREIGN KEY(Id_voyage) REFERENCES voyage(Id_voyage),
   FOREIGN KEY(Id_place) REFERENCES place(Id_place)
);
