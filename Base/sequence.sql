-- Script de réparation des séquences
DO $$
DECLARE
    max_id BIGINT;
    seq_name TEXT;
BEGIN
    -- Table reservation
    SELECT COALESCE(MAX(id_reservation), 0) INTO max_id FROM reservation;
    PERFORM setval('reservation_id_reservation_seq', max_id + 1);
    RAISE NOTICE 'Séquence reservation réinitialisée à %', max_id + 1;
    
    -- Table facture
    SELECT COALESCE(MAX(id_facture), 0) INTO max_id FROM facture;
    PERFORM setval('facture_id_facture_seq', max_id + 1);
    RAISE NOTICE 'Séquence facture réinitialisée à %', max_id + 1;
    
    -- Table nbr_place_reservation
    SELECT COALESCE(MAX(id_nbr_place_reservation), 0) INTO max_id FROM nbr_place_reservation;
    PERFORM setval('nbr_place_reservation_id_nbr_place_reservation_seq', max_id + 1);
    RAISE NOTICE 'Séquence nbr_place_reservation réinitialisée à %', max_id + 1;
    
    -- Table billet
    SELECT COALESCE(MAX(id_billet), 0) INTO max_id FROM billet;
    PERFORM setval('billet_id_billet_seq', max_id + 1);
    RAISE NOTICE 'Séquence billet réinitialisée à %', max_id + 1;
    
    -- Table place
    SELECT COALESCE(MAX(id_place), 0) INTO max_id FROM place;
    PERFORM setval('place_id_place_seq', max_id + 1);
    RAISE NOTICE 'Séquence place réinitialisée à %', max_id + 1;
    
    -- Table voyage
    SELECT COALESCE(MAX(id_voyage), 0) INTO max_id FROM voyage;
    PERFORM setval('voyage_id_voyage_seq', max_id + 1);
    RAISE NOTICE 'Séquence voyage réinitialisée à %', max_id + 1;
END $$;