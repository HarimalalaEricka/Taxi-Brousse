-- ==========================================================
-- Script d'insertion des PlaceVoyage pour les voyages existants
-- ==========================================================

-- Insérer les PlaceVoyage pour chaque combinaison (place, voyage)
-- où le véhicule du voyage correspond au véhicule de la place

INSERT INTO place_voyage (statut, id_place, id_voyage)
SELECT 'LIBRE', p.id_place, v.id_voyage
FROM place p
JOIN voyage v ON p.id_vehicule = v.id_vehicule
WHERE NOT EXISTS (
    SELECT 1 FROM place_voyage pv 
    WHERE pv.id_place = p.id_place 
    AND pv.id_voyage = v.id_voyage
);

-- Vérification : afficher le nombre de PlaceVoyage par voyage
SELECT 
    v.id_voyage,
    v.date_depart,
    v.heure_depart,
    COUNT(pv.id_place_voyage) as nb_places,
    COUNT(CASE WHEN pv.statut = 'LIBRE' THEN 1 END) as libres,
    COUNT(CASE WHEN pv.statut = 'RESERVEE' THEN 1 END) as reservees
FROM voyage v
LEFT JOIN place_voyage pv ON pv.id_voyage = v.id_voyage
GROUP BY v.id_voyage, v.date_depart, v.heure_depart
ORDER BY v.id_voyage;
