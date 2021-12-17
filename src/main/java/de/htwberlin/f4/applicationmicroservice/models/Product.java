package de.htwberlin.f4.applicationmicroservice.models;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Model der Entitaet Product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(value = AccessLevel.PACKAGE)
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(name = "size", nullable = false, columnDefinition = "TEXT")
    private String size;
    @Column(name = "color", nullable = false, columnDefinition = "TEXT")
    private String color;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "weight", nullable = false)
    private Double weight;
    @Transient
    private Double mehrwertsteuer;

    /**
     * Konstructor mit begrenzten Parametern.
     * @param name String Name des Produktes
     * @param description String Beschreibung des Produktes
     * @param size String Groeße des Produktes
     * @param color String Farbe des Produktes
     * @param price Double Netto Betrag des Produktes
     * @param weight Double Gewicht des Produktes
     */
    public Product(String name, String description, String size, String color, Double price, Double weight) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.weight = weight;
    }

    /**
     * @return Double return the mehrwertsteuer
     */
    public Double getMehrwertsteuer() {
        return mehrwertsteuer;
    }

    /**
     * @param mehrwertsteuer the mehrwertsteuer to set
     */
    public void setMehrwertsteuer(Double mehrwertsteuer) {
        this.mehrwertsteuer = mehrwertsteuer;
    }

}

