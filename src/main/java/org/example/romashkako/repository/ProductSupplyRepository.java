package org.example.romashkako.repository;

import org.example.romashkako.model.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply,Long> {
}
