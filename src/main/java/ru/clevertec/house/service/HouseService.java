package ru.clevertec.house.service;

import ru.clevertec.house.dto.response.PersonWithoutLiveHouseResponse;

import java.util.List;
import java.util.UUID;

public interface HouseService<HouseResponse, HouseRequest>
        extends IService<HouseResponse, HouseRequest> {

    /**
     * Возвращает всех людей, которые живут в доме
     *
     * @param uuid идентификатор дома
     * @return лист с информацией о людях
     */
    public List<PersonWithoutLiveHouseResponse> getPersonsLivingInHouse(UUID uuid);
}
