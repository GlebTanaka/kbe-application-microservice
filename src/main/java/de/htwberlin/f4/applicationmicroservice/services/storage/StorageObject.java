package de.htwberlin.f4.applicationmicroservice.services.storage;

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
public class StorageObject {

    @JsonProperty("id")
    private String Id;
    @JsonProperty("amount")
    private Integer amount;
    //TODO change Integer to Period or Duration?
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("place")
    private String place;
}

