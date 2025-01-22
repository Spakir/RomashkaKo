package org.example.romashkako.repository;

import org.example.romashkako.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT * FROM products p 
                     
            WHERE (COALESCE(:filterName, '') = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :filterName, '%'))) AND 
                  (:minPrice IS NULL OR (p.price >= :minPrice)) AND 
                  (:maxPrice IS NULL OR (p.price <= :maxPrice)) AND 
                  (:inStock IS NULL OR (p.in_stock = :inStock))
            
            ORDER BY CASE WHEN :sortType = 'name' AND :sortDirection = 'ASC' THEN p.name END ASC,
                     CASE WHEN :sortType = 'name' AND :sortDirection = 'DESC' THEN p.name END DESC,
                     CASE WHEN :sortType = 'price' AND :sortDirection = 'ASC' THEN p.price END ASC,
                     CASE WHEN :sortType = 'price' AND :sortDirection = 'DESC' THEN p.price END DESC
            
            LIMIT :limit OFFSET :offset * :limit
            """,nativeQuery = true)
    List<Product> findByFilters(@Param("filterName") String filterName,
                                @Param("minPrice") Integer minPrice,
                                @Param("maxPrice") Integer maxPrice,
                                @Param("inStock") Boolean inStock,
                                @Param("sortType") String sortType,
                                @Param("sortDirection") String sortDirection,
                                @Param("limit") int limit,
                                @Param("offset") int offset);
}
