-- =========================
-- Script de migration pour ajouter la table place_voyage
-- Cette table permet de gérer les places par voyage au lieu de par véhicule
-- Ainsi, le même véhicule peut avoir des réservations différentes selon les voyages
-- =========================

-- Création du type si non existant (sinon ignorer)
-- CREATE TYPE status_place AS ENUM ('LIBRE', 'RESERVEE', 'OCCUPEE');

-- =========================
-- TABLE : PLACE_VOYAGE
-- Liaison entre Place et Voyage avec statut spécifique
-- =========================
CREATE TABLE place_voyage (
   id_place_voyage SERIAL,
   statut VARCHAR(20) CHECK (statut IN ('LIBRE', 'RESERVEE', 'OCCUPEE')),
   id_place INTEGER NOT NULL,
   id_voyage INTEGER NOT NULL,
   PRIMARY KEY(id_place_voyage),
   FOREIGN KEY(id_place) REFERENCES place(id_place),
   FOREIGN KEY(id_voyage) REFERENCES voyage(id_voyage),
   UNIQUE(id_place, id_voyage)
);

-- =========================
-- MODIFICATION TABLE BILLET
-- Changer la référence de id_place vers id_place_voyage
-- =========================

-- Ajouter la nouvelle colonne
ALTER TABLE billet ADD COLUMN id_place_voyage INTEGER;

-- Mettre à jour la contrainte de clé étrangère
ALTER TABLE billet ADD CONSTRAINT fk_billet_place_voyage 
    FOREIGN KEY (id_place_voyage) REFERENCES place_voyage(id_place_voyage);

-- Optionnel: Si vous avez des données existantes, vous devrez les migrer
-- La migration des données existantes nécessiterait:
-- 1. Créer les PlaceVoyage correspondants pour les voyages existants
-- 2. Mettre à jour les billets avec les nouveaux id_place_voyage

-- =========================
-- Script pour initialiser les PlaceVoyage pour les voyages existants
-- =========================
-- INSERT INTO place_voyage (statut, id_place, id_voyage)
-- SELECT 'LIBRE', p.id_place, v.id_voyage
-- FROM place p
-- CROSS JOIN voyage v
-- WHERE p.id_vehicule = v.id_vehicule
-- AND NOT EXISTS (
--     SELECT 1 FROM place_voyage pv 
--     WHERE pv.id_place = p.id_place AND pv.id_voyage = v.id_voyage
-- );
