package de.htwberlin.f4.applicationmicroservice.services.googlemaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult {

    private List<GeocodeObject> results;
    private String status;
}

