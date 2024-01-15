package ru.clevertec.house.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.house.entity.model.Passport;
import ru.clevertec.house.entity.model.Sex;

import java.util.UUID;

/**
 * Класс определяющий запрос от пользователя для Person
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    String name;
    String surname;
    Sex sex;
    Passport passport;

    @JsonProperty("uuid_house")
    UUID uuidHouse;
}
