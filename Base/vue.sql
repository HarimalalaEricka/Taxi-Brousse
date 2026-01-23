create or replace view vue_prix_billet as
select c.categorie, c.reduction_fixe, c.reduction_pourcentage,
        tp.type, pb.prix, pb.Id_trajet, (pb.prix - c.reduction_fixe) as prix1,
        ((pb.prix - c.reduction_fixe) - (pb.prix * c.reduction_pourcentage / 100)) as prix_final,
        tp.id_type_place
from Categorie_personne c
join type_place tp
join prix_billet pb 
on pb.id_type_place = tp.id_type_place 
on c.id_type_place = tp.id_type_place;