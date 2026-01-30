-- Migration script to add payment status and amount to billet table
-- This allows tracking payment status and amount for each ticket

ALTER TABLE billet ADD COLUMN id_etat_paiement INTEGER NOT NULL DEFAULT 2;
ALTER TABLE billet ADD CONSTRAINT fk_billet_etat_paiement FOREIGN KEY (id_etat_paiement) REFERENCES etat_paiement(id_etat_paiement);

ALTER TABLE billet ADD COLUMN montant DECIMAL(10,2);