package ru.clevertec.house.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.house.entity.model.Passport;
import ru.clevertec.house.entity.model.Sex;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс определяющий ответ от севера для Person
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PersonResponse {

    UUID uuid;
    String name;
    String surname;
    Sex sex;
    Passport passport;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime updateDate;

    HouseResponse liveHouse;
}
