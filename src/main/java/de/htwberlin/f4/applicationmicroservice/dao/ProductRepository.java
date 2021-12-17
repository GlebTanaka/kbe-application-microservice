package de.htwberlin.f4.applicationmicroservice.dao;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.models.SimpleProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Klasse als DAO fuer Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<SimpleProduct> findAllSimpleProducts();
}

