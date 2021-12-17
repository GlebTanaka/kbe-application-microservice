package de.htwberlin.f4.applicationmicroservice.models;

import java.util.UUID;

/**
 * verkleinert das Produkt auf ID und name
 */
public interface SimpleProduct {
    /**
     * getter ID
     * @return UUID
     */
    UUID getId();
    /**
     * getter Name
     * @return String
     */
    String getName();
}
