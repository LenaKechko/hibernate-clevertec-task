package ru.clevertec.house.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Класс определяющий запрос от пользователя для House
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequest {

    Double area;
    String country;
    String city;
    String street;
    Integer numberHouse;

    @JsonProperty("uuid_owners")
    List<UUID> uuidOwners;

}
