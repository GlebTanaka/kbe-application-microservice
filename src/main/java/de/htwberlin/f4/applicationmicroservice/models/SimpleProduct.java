package de.htwberlin.f4.applicationmicroservice.models;

import java.util.UUID;

/**
 * verkleinert das Produkt auf ID und name
 */
public interface SimpleProduct {
    UUID getId();
    String getName();
}
