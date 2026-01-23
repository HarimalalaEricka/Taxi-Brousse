-- =========================
-- TABLE : ROLE
-- =========================
CREATE TABLE role (
    id_role SERIAL PRIMARY KEY,
    nom_role VARCHAR(30) UNIQUE NOT NULL
);

-- =========================
-- TABLE : UTILISATEUR
-- =========================
CREATE TABLE utilisateur (
    id_utilisateur SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    telephone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    statut VARCHAR(20) CHECK (statut IN ('actif', 'inactif')),
    id_role INT NOT NULL,
    FOREIGN KEY (id_role) REFERENCES role(id_role)
);

-- =========================
-- TABLE : VEHICULE
-- =========================
CREATE TABLE vehicule (
    id_vehicule SERIAL PRIMARY KEY,
    immatriculation VARCHAR(30) UNIQUE NOT NULL,
    nombre_places INT NOT NULL CHECK (nombre_places > 0),
    type_vehicule VARCHAR(20),
    etat VARCHAR(20) CHECK (etat IN ('disponible', 'en_route', 'en_panne'))
);

-- =========================
-- TABLE : CHAUFFEUR
-- =========================
CREATE TABLE chauffeur (
    id_chauffeur SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    telephone VARCHAR(20),
    statut VARCHAR(20) CHECK (statut IN ('actif', 'inactif'))
);

-- =========================
-- TABLE : TRAJET
-- =========================
CREATE TABLE trajet (
    id_trajet SERIAL PRIMARY KEY,
    ville_depart VARCHAR(50) NOT NULL,
    ville_arrivee VARCHAR(50) NOT NULL,
    distance DECIMAL(6,2),
    duree_estimee INT
);

-- =========================
-- TABLE : GARE
-- =========================
CREATE TABLE gare (
    id_gare SERIAL PRIMARY KEY,
    nom_gare VARCHAR(50) NOT NULL,
    ville VARCHAR(50) NOT NULL
);

-- =========================
-- TABLE : VOYAGE
-- =========================
CREATE TABLE voyage (
    id_voyage SERIAL PRIMARY KEY,
    date_depart DATE NOT NULL,
    heure_depart TIME NOT NULL,
    prix_billet DECIMAL(8,2) NOT NULL,
    etat_voyage VARCHAR(20) CHECK (
        etat_voyage IN ('en_attente', 'en_cours', 'termine', 'annule')
    ),
    id_vehicule INT NOT NULL,
    id_chauffeur INT NOT NULL,
    id_trajet INT NOT NULL,
    id_gare INT NOT NULL,
    FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule),
    FOREIGN KEY (id_chauffeur) REFERENCES chauffeur(id_chauffeur),
    FOREIGN KEY (id_trajet) REFERENCES trajet(id_trajet),
    FOREIGN KEY (id_gare) REFERENCES gare(id_gare)
);

-- =========================
-- TABLE : PLACE
-- =========================
CREATE TABLE place (
    id_place SERIAL PRIMARY KEY,
    numero_place INT NOT NULL,
    statut VARCHAR(20) CHECK (statut IN ('libre', 'reservee', 'occupee')),
    id_voyage INT NOT NULL,
    FOREIGN KEY (id_voyage) REFERENCES voyage(id_voyage),
    UNIQUE (numero_place, id_voyage)
);

-- =========================
-- TABLE : RESERVATION
-- =========================
CREATE TABLE reservation (
    id_reservation SERIAL PRIMARY KEY,
    numero_billet VARCHAR(30) UNIQUE NOT NULL,
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    statut VARCHAR(20) CHECK (
        statut IN ('reservee', 'confirmee', 'annulee')
    ),
    id_utilisateur INT NOT NULL,
    id_voyage INT NOT NULL,
    id_place INT NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur),
    FOREIGN KEY (id_voyage) REFERENCES voyage(id_voyage),
    FOREIGN KEY (id_place) REFERENCES place(id_place)
);

-- =========================
-- TABLE : PAIEMENT
-- =========================
CREATE TABLE paiement (
    id_paiement SERIAL PRIMARY KEY,
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montant DECIMAL(8,2) NOT NULL,
    mode_paiement VARCHAR(30),
    statut_paiement VARCHAR(20) CHECK (
        statut_paiement IN ('valide', 'refuse')
    ),
    id_reservation INT UNIQUE,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation)
);

-- =========================
-- TABLE : SOCIETE
-- =========================
CREATE TABLE societe (
    id_societe SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(100)
);

-- =========================
-- TABLE : TYPE_PRESTATION
-- =========================
CREATE TABLE type_prestation (
    id_type_prestation SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- =========================
-- TABLE : TARIF_PRESTATION
-- =========================
CREATE TABLE tarif_prestation (
    id_tarif_prestation SERIAL PRIMARY KEY,
    id_type_prestation INT NOT NULL,
    prix_unitaire DECIMAL(12,2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    FOREIGN KEY (id_type_prestation) REFERENCES type_prestation(id_type_prestation)
);

-- =========================
-- TABLE : PRESTATION
-- =========================
CREATE TABLE prestation (
    id_prestation SERIAL PRIMARY KEY,
    id_societe INT NOT NULL,
    id_tarif_prestation INT NOT NULL,
    id_etat_paiement INT NOT NULL,
    id_voyage INT,
    quantite INT NOT NULL DEFAULT 1,
    date_prestation DATE NOT NULL,
    FOREIGN KEY (id_societe) REFERENCES societe(id_societe),
    FOREIGN KEY (id_tarif_prestation) REFERENCES tarif_prestation(id_tarif_prestation),
    FOREIGN KEY (id_etat_paiement) REFERENCES etat_paiement(id_etat_paiement),
    FOREIGN KEY (id_voyage) REFERENCES voyage(id_voyage)
);

-- =========================
-- TABLE : PAIEMENT_PRESTATION
-- =========================
CREATE TABLE paiement_prestation (
    id_paiement SERIAL PRIMARY KEY,
    id_prestation INT NOT NULL,
    montant DECIMAL(12,2) NOT NULL,
    date_paiement DATE NOT NULL,
    FOREIGN KEY (id_prestation) REFERENCES prestation(id_prestation)
);
