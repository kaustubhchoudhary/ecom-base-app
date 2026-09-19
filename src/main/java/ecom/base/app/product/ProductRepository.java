package ecom.base.app.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived Queries
    boolean existsByName(String name);

    // SELECT * FROM products where price BETWEEN min AND max;
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    long countProductsByCategoryId(Long categoryId);

    // Find all products of a particular brand, sorted by price in descending order.
    List<Product> findByBrandOrderByPriceDesc(String brand);

    // JPQL - method names don't need to have keywords
    // You could name them anything BUT let them me meaningful
    @Query("SELECT p FROM Product p WHERE p.price < :price AND brand = :brand")
    List<Product> letsGetSomeProducts(@Param("price") BigDecimal costPrice, @Param("brand") String brandName);

    // Native Query
    @Query(value = "SELECT * FROM ecommerce.products WHERE product_id > :id", nativeQuery = true)
    List<Product> fetchProductsWithIdGreaterThan(Long id);

    // Data modifying JPQL
    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.active = false WHERE p.id = :id")
    int deactivateById(@Param("id") Long id);

}
