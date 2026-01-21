package com.taxi.repository;

import com.taxi.vue.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository  
public interface VuePrixBilletRepository extends JpaRepository<VuePrixBillet, Long> {
    
    // Si vous avez une entité pour la vue
    // @Query(value = "SELECT * FROM vue_prix_billet " +
    //                "WHERE id_trajet = :idTrajet " +
    //                "AND categorie = :categorie " +
    //                "AND id_type_place = :idTypePlace", 
    //        nativeQuery = true)
    // VuePrixBillet findByTrajetCategorieTypePlace(
    //     @Param("idTrajet") Long idTrajet,
    //     @Param("categorie") String categorie,
    //     @Param("idTypePlace") Long idTypePlace
    // );
    
    // Ou juste pour récupérer le prix
    @Query(value = "SELECT prix_final FROM vue_prix_billet " +
                   "WHERE id_trajet = :idTrajet " +
                   "AND categorie = :categorie " +
                   "AND id_type_place = :idTypePlace", 
           nativeQuery = true)
    Double findPrixFinal(
        @Param("idTrajet") Long idTrajet,
        @Param("categorie") String categorie,
        @Param("idTypePlace") Long idTypePlace
    );
}