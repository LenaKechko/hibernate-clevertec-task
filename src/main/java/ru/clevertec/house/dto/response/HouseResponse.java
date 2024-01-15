package ru.clevertec.house.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс определяющий ответ от севера для House
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class HouseResponse {

    UUID uuid;
    Double area;
    String country;
    String city;
    String street;
    Integer numberHouse;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime createDate;

}
