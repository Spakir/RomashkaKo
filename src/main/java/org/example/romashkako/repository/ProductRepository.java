package org.example.romashkako.repository;

import org.example.romashkako.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
                   SELECT p from Product p WHERE 
                   (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND 
                   (:minPrice IS NULL OR (p.price >= :minPrice)) AND 
                   (:maxPrice IS NULL OR (p.price <= :maxPrice)) AND 
                   (:inStock IS NULL OR (p.inStock = :inStock))           
            """)
    List<Product> findByFilters(@Param("name") String name,
                                @Param("minPrice") Integer minPrice,
                                @Param("maxPrice") Integer maxPrice,
                                @Param("inStock") Boolean inStock);
}
