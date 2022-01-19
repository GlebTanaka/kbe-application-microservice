package de.htwberlin.f4.applicationmicroservice.models.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeGeometry {

    @JsonProperty("location")
    private GeocodeLocation geocodeLocation;
}

