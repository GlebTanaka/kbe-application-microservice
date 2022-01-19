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
public class GeocodeLocation {

    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
}

