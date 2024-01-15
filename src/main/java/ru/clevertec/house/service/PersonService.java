package ru.clevertec.house.service;

import ru.clevertec.house.dto.response.HouseResponse;

import java.util.List;
import java.util.UUID;

public interface PersonService<PersonResponse, PersonRequest>
        extends IService<PersonResponse, PersonRequest> {

    /**
     * Возвращает все дома, которыми человек владеет
     *
     * @param uuid идентификатор человека
     * @return лист с информацией о домах
     */
    public List<HouseResponse> getOwningHouses(UUID uuid);
}
