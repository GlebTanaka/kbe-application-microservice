package de.htwberlin.f4.applicationmicroservice.models.product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(value = AccessLevel.PUBLIC)
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
    @Schema( description = "amount is stored externally")
    private Integer amount;
    @Transient
    @Schema(description = "amount is stored externally")
    private String place;
    @Transient
    @Schema(description = "mehrwersteuer is requested from calculator-microservice")
    private Double mehrwertsteuer;
    @Transient
    @Schema(description = "Address is requested from google-map-api")
    private String formattedAddress;
    @Transient
    @Schema(description = "latitude is requested from google-map-api")
    private String lat;
    @Transient
    @Schema(description = "longitude is requested from google-map-api")
    private String lng;
    @Transient
    @Schema(description = "delivery date is stored externally")
    private Date deliveryDate;
    @Transient
    private Integer deliveryTime;

    public Product(String name, String description, String size, String color, Double price, Double weight, Integer amount, Integer duration, String place) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.weight = weight;
        this.amount = amount;
        this.deliveryTime = duration;
        this.place = place;
    }
}

