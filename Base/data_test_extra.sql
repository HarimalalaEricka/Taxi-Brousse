-- Données de test pour l'extra "Eau" à 5000 Ar

-- Ajout de l'extra Eau
INSERT INTO extra (id_extra, nom, prix, stock_initial) VALUES (1, 'Eau', 5000, 100);

-- Approvisionnement (achats de stock)
INSERT INTO extra_achat (id_achat, id_extra, quantite, date_achat) VALUES (1, 1, 50, '2026-01-01');
INSERT INTO extra_achat (id_achat, id_extra, quantite, date_achat) VALUES (2, 1, 30, '2026-01-15');

-- Ventes (liées à des réservations)
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite) VALUES (1, 1, 1, 10);
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite) VALUES (2, 1, 2, 5);
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite) VALUES (3, 1, 3, 8);


--------------------------------
-- Table extra (stock initial = 100, prix = 5000)
INSERT INTO extra (id_extra, nom, prix, stock_initial) VALUES (1, 'Eau', 5000, 100);

-- Table extra_achat (approvisionnement du stock)
INSERT INTO extra_achat (id_achat, id_extra, quantite, date_achat) VALUES (1, 1, 50, '2026-01-01');
INSERT INTO extra_achat (id_achat, id_extra, quantite, date_achat) VALUES (2, 1, 30, '2026-01-15');

-- Table extra_reservation (ventes aux clients)
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite, date_reservation) VALUES (1, 1, 1, 10, '2026-01-05');
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite, date_reservation) VALUES (2, 1, 2, 5, '2026-01-10');
INSERT INTO extra_reservation (id_extra_reservation, id_extra, id_reservation, quantite, date_reservation) VALUES (3, 1, 3, 8, '2026-01-20');

update extra_reservation set date_reservation = '2026-01-05' where id_extra_reservation > 1;

-- Table voyage (exemple pour CA voyages)
INSERT INTO voyage (id_voyage, ...) VALUES (1, ...);
-- Table prestation (exemple pour CA prestations)
INSERT INTO prestation (id_prestation, montant, ...) VALUES (1, 20000, ...);

-- Table reservation (liée aux ventes d'extras)
INSERT INTO reservation (id_reservation, ...) VALUES (1, ...), (2, ...), (3, ...);


<div style="flex: 1; background: #3a7e7e; padding: 25px; border-radius: 12px; text-align: center; color: white; box-shadow: 0 4px 15px rgba(0,0,0,0.15);">
                <p style="font-size: 14px; opacity: 0.9; margin-bottom: 5px;">🥤 CA Extras (vendu)</p>
                <p style="font-size: 28px; font-weight: bold; margin: 0;" th:text="${#numbers.formatDecimal(chiffreAffaire.caExtras, 0, 'COMMA', 0, 'POINT')} + ' Ar'">0 Ar</p>
                <p style="font-size: 13px; opacity: 0.8; margin-top: 8px;">Théorique : <span th:text="${#numbers.formatDecimal(chiffreAffaire.caExtrasTheorique, 0, 'COMMA', 0, 'POINT')} + ' Ar'"></span></p>
            </div>

            <div style="flex: 1; background: #3a7e7e; padding: 25px; border-radius: 12px; text-align: center; color: white; box-shadow: 0 4px 15px rgba(0,0,0,0.15);">
                <p style="font-size: 14px; opacity: 0.9; margin-bottom: 5px;"></p>
                <p style="font-size: 28px; font-weight: bold; margin: 0;" th:text="${#numbers.formatDecimal(chiffreAffaire.caExtras, 0, 'COMMA', 0, 'POINT')} + ' Ar'">0 Ar</p>
                -- <p style="font-size: 13px; opacity: 0.8; margin-top: 8px;">Théorique : <span th:text="${#numbers.formatDecimal(chiffreAffaire.caExtrasTheorique, 0, 'COMMA', 0, 'POINT')} + ' Ar'"></span></p>
            </div>