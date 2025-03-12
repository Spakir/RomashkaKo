package org.example.romashkako.repository;

import org.example.romashkako.model.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Long> {

    @Query("SELECT ps FROM ProductSupply ps JOIN FETCH ps.product")
    List<ProductSupply> findAll();
}
