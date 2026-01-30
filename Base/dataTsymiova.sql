Insert into role(nom_role) values
('admin'),
('agent'),
('client diver'),
('client special');

insert into utilisateur(nom, prenom, telephone, email, adresse, statut, mot_de_passe, Id_Role) values
('Admin', 'Super', '0000000000', 'admin@example.com', 'Adresse Admin', 'ACTIF', 'admin123', 1),
('Agent', 'Service', '1111111111', 'agent@example.com', 'Adresse Agent', 'ACTIF', 'agent123', 2),
('Client', 'Divers', '0000000000', null, null, 'ACTIF', null, 3);

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

insert into etat_vehicule(status) values
('disponible'),
('en route'),
('hors service');

insert into etat_paiement(etat) values
('paye'),
('non paye'),
('partiellement paye');