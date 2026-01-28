\c postgres
drop database taxi_brousse;
create database taxi_brousse;
\c taxi_brousse

delete from billet;
-- update categorie_personne;
delete from chauffeur;
delete from facture;
delete from nbr_place_reservation;
delete from paiement;
delete from paiement_prestation;
delete from place;
delete from plusieur_paiement;
delete from prestation;
delete from reservation;
delete from societe;
delete from tarif_prestation;
delete from trajet;
delete from type_prestation;
delete from vehicule;
delete from voyage;

trajet (TNR - Toamasina, TNR - Majunga)
vehicule(1244 TBK)
voyage ( 20 janv 2026 - 10h, 21 janv 2026 - 15h , 21 janv 2026 - 15h)

20 janv 2026 - 10h
    adulte eco 40
    pub Vaniala 1, Lewis 1

21 janv 2026 - 15h  
    adulte eco 30
    pub socobis 2, jejoo 1

21 janv 2026 - 15h
    adulte eco 50
    pub 0