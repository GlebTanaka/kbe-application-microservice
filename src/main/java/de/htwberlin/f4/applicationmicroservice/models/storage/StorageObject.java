package de.htwberlin.f4.applicationmicroservice.models.storage;

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
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("place")
    private String place;
}

