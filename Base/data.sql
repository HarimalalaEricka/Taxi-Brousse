Insert into role(nom_role) values
('admin'),
('agent'),
('client diver'),
('client special');

-- INSERT INTO statut_utilisateur (enumlabel) VALUES
-- ('actif'),
-- ('inactif');

insert into utilisateur(nom, prenom, telephone, email, adresse, statut, mot_de_passe, Id_Role) values
('Admin', 'Super', '0000000000', 'admin@example.com', 'Adresse Admin', 'ACTIF', 'admin123', 1),
('Agent', 'Service', '1111111111', 'agent@example.com', 'Adresse Agent', 'ACTIF', 'agent123', 2),
('Client', 'Divers', '0000000000', null, null, 'ACTIF', null, 3);

insert into chauffeur(nom, prenom, telephone, status) values
('Doe', 'John', '2223334444', 'ACTIF'),
('Smith', 'Jane', '5556667777', 'ACTIF');

insert into trajet(ville_depart, ville_arrivee, distance, duree_estimee) values
('Antananarivo', 'Toamasina', 600.50, 300),
('Antsirabe', 'Fianarantsoa', 200.00, 150);

insert into etat_voyage(etat) values
('en attente'),
('en cours'),
('termine'),
('annule');

insert into type_paiement(type) values
('espece'),
('carte bancaire'),
('mobile money');

insert into type_place(type) values
('standard'),
('premium'),
('vip');

INSERT INTO Categorie_personne(categorie, reduction_pourcentage, reduction_fixe, Id_type_place) VALUES
('adulte', 0, 0, 1),           
('adulte', 0, 0, 2),           
('adulte', 0, 0, 3),
('enfant', 20, 0, 1),           
('enfant', 0, 10000, 2),           
('enfant', 0, 5000, 3),     
('senior', 20, 0, 1),           
('senior', 20, 0, 2),           
('senior', 20, 0, 3);

insert into prix_billet(prix, date_debut, date_fin, Id_trajet, Id_type_place) values
(50000.00, '2026-01-01', null, 1, 1), 
(60000.00, '2026-01-01', null, 1, 2),
(70000.00, '2026-01-01', null, 1, 3),
 


(60000.00, '2026-01-01', null, 2, 1),
(90000.00, '2026-01-01', null, 2, 2);

insert into etat_vehicule(status) values
('disponible'),
('en route'),
('hors service');

insert into etat_paiement(etat) values
('paye'),
('non paye'),
('partiellement paye');

INSERT INTO vehicule(immatriculation, nombre_places, Id_etat_vehicule) VALUES
('1234-AB', 12, 1),
('5678-CD', 20, 1);


INSERT INTO voyage(date_depart, heure_depart, Id_trajet, Id_chauffeur, Id_vehicule, Id_etat_voyage) VALUES
('2026-01-09', '08:00:00', 1, 1, 1, 1),
('2026-01-09', '09:30:00', 2, 2, 2, 1);

Insert into place(numero_place, statut, Id_vehicule, Id_type_place) values
('1V1', 'LIBRE', 1, 1),
('1V2', 'LIBRE', 1, 1),
('1V3', 'LIBRE', 1, 1),
('1V4', 'LIBRE', 1, 1),
('1V5', 'LIBRE', 1, 1),
('1V6', 'LIBRE', 1, 1),
('1V7', 'LIBRE', 1, 1),
('1V8', 'LIBRE', 1, 1),

('1V11', 'LIBRE', 1, 2),
('1V12', 'LIBRE', 1, 2),
('1V12', 'LIBRE', 1, 2),
('1V12', 'LIBRE', 1, 2),

('1V12', 'LIBRE', 1, 3),
('1V12', 'LIBRE', 1, 3),
('1V12', 'LIBRE', 1, 3),
('1V12', 'LIBRE', 1, 3),
('1V12', 'LIBRE', 1, 3),
('1V9', 'LIBRE', 1, 3),
('1V10', 'LIBRE', 1, 3),
('1V10', 'LIBRE', 1, 3),

('2V1', 'LIBRE', 2, 1),
('2V2', 'LIBRE', 2, 1),
('2V3', 'LIBRE', 2, 1),
('2V4', 'LIBRE', 2, 1),
('2V5', 'LIBRE', 2, 1),
('2V6', 'LIBRE', 2, 1),
('2V7', 'LIBRE', 2, 1),
('2V8', 'LIBRE', 2, 1),
('2V9', 'LIBRE', 2, 1),
('2V10', 'LIBRE', 2, 1),
('2V11', 'LIBRE', 2, 2),
('2V12', 'LIBRE', 2, 1),
('2V11', 'LIBRE', 2, 2),
('2V12', 'LIBRE', 2, 1),
('2V13', 'LIBRE', 2, 1),
('2V14', 'LIBRE', 2, 1),
('2V15', 'LIBRE', 2, 1),
('2V16', 'LIBRE', 2, 1),
('2V17', 'LIBRE', 2, 1),
('2V18', 'LIBRE', 2, 1),
('2V19', 'LIBRE', 2, 1),
('2V20', 'LIBRE', 2, 1);

select c.categorie, c.reduction_fixe, c.reduction_pourcentage,
        tp.type, pb.prix, pb.Id_trajet, (pb.prix - c.reduction_fixe) as prix1,
        ((pb.prix - c.reduction_fixe) - (pb.prix * c.reduction_pourcentage / 100)) as prix_final,
        tp.id_type_place
from Categorie_personne c
join type_place tp
join prix_billet pb 
on pb.id_type_place = tp.id_type_place 
on c.id_type_place = tp.id_type_place;




select pb.*, pl.statut, pl.Id_vehicule 
from vue_prix_billet pb
join place pl 
on pb.id_type_place = pl.id_type_place;


insert into facture( id_facture, num_facture, date, montant, Id_etat_paiement, Id_utilisateur) values
(1, 'FAC001', '2026-01-05', 3000, 2, 3);

insert into Reservation(id_reservation, au_nom_de, date_reservation, statut, id_facture, id_utilisateur, id_voyage) values
(1, 'Jean Dupont', '2026-01-04', 'RESERVEE', 1, 3, 1);

insert into nbr_place_reservation(id_reservation, id_categorie_personne, nbr_place) values
(1, 1, 4),
(1, 2, 2),
(1, 3, 4),
(1, 4, 2),
(1, 5, 1),
(1, 6, 2),
(1, 6, 2),
(1, 7, 1),
(1, 8, 2);

update prix_billet set prix = 55000 where id_prix_billet = 1;
update categorie_personne set reduction_fixe = 15000 where id_categorie_personne = 4;


-- ==========================================
-- DONNÉES DE TEST - PRESTATIONS PUBLICITAIRES
-- ==========================================

-- Types de prestation
INSERT INTO type_prestation (nom, description) VALUES
('diffusion video', 'Diffusion de vidéos publicitaires sur écran passager'),
('diffusion audio', 'Diffusion de spots audio'),
('affichage', 'Affichage publicitaire sur véhicule');

-- Sociétés clientes
INSERT INTO societe (nom, adresse, contact) VALUES
('Vaniala', 'Antananarivo', '034 00 000 00'),
('Lewis', 'Antananarivo', '034 11 111 11');

-- Tarif diffusion video: 100 000 Ar (actif depuis 01/01/2025)
INSERT INTO tarif_prestation (prix_unitaire, date_debut, date_fin, id_type_prestation) VALUES
(100000, '2025-01-01', NULL, 1);

-- Prestations de décembre 2025
-- Vaniala: 20 diffusions = 20 x 100 000 = 2 000 000 Ar, a payé 1 000 000 Ar, reste 1 000 000 Ar
-- Lewis: 10 diffusions = 10 x 100 000 = 1 000 000 Ar, payé intégralement
-- Total = 3 000 000 Ar

INSERT INTO prestation (date_prestation, quantite, id_societe, id_tarif_prestation, id_etat_paiement, id_voyage) VALUES
('2025-12-15', 20, 1, 1, 3, NULL),  -- Vaniala: 20 diffusions, partiellement payé
('2025-12-20', 10, 2, 1, 1, NULL);  -- Lewis: 10 diffusions, payé intégralement

-- ==========================================
-- PAIEMENTS PRESTATIONS (historique)
-- ==========================================
INSERT INTO paiement_prestation (id_prestation, montant, date_paiement) VALUES
(1, 1000000, '2025-12-15'),  -- Vaniala paie 1M Ar le 15 décembre 2025
(2, 1000000, '2025-12-20');  -- Lewis paie 1M Ar le 20 décembre 2025

-- ==========================================
-- VÉRIFICATION
-- ==========================================
SELECT 
    s.nom AS societe,
    tp.nom AS type_prestation,
    p.quantite,
    t.prix_unitaire,
    (p.quantite * t.prix_unitaire) AS montant
FROM prestation p
JOIN societe s ON s.id_societe = p.id_societe
JOIN tarif_prestation t ON t.id_tarif_prestation = p.id_tarif_prestation
JOIN type_prestation tp ON tp.id_type_prestation = t.id_type_prestation
WHERE EXTRACT(MONTH FROM p.date_prestation) = 12 
  AND EXTRACT(YEAR FROM p.date_prestation) = 2025;

-- Résultat attendu:
-- | societe  | type_prestation  | quantite | prix_unitaire | montant    |
-- |----------|------------------|----------|---------------|------------|
-- | Vaniala  | diffusion video  | 20       | 100000        | 2000000    |
-- | Lewis    | diffusion video  | 10       | 100000        | 1000000    |
-- | TOTAL    |                  | 30       |               | 3000000    |

-- VÉRIFICATION RESTE À PAYER AU 14 DÉCEMBRE 2025 (avant paiement Vaniala)
-- SELECT p.id_prestation, s.nom, 
--        (p.quantite * t.prix_unitaire) as total,
--        COALESCE(SUM(pp.montant), 0) as paye
-- FROM prestation p
-- JOIN societe s ON s.id_societe = p.id_societe
-- JOIN tarif_prestation t ON t.id_tarif_prestation = p.id_tarif_prestation
-- LEFT JOIN paiement_prestation pp ON pp.id_prestation = p.id_prestation AND pp.date_paiement <= '2025-12-14'
-- GROUP BY p.id_prestation, s.nom, p.quantite, t.prix_unitaire;
-- Vaniala doit 2M Ar (aucun paiement avant le 15)

-- VÉRIFICATION RESTE À PAYER AU 15 JANVIER 2026 (après paiement Vaniala)
-- Vaniala doit 1M Ar (a payé 1M sur 2M)