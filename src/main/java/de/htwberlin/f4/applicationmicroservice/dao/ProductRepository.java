package de.htwberlin.f4.applicationmicroservice.dao;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Klasse als DAO fuer Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}

