package ru.clevertec.house.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.entity.House;


@Mapper(componentModel = "spring")
public interface HouseMapper {

    /**
     * Маппит Request в сущность без id и UUID
     * Дата создания создается по умолчанию
     *
     * @param houseDto - DTO для маппинга
     * @return новый дом
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)//, expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    House toHouse(HouseRequest houseDto);

    /**
     * Маппит текущий House в Response
     *
     * @param house - существующий дом
     * @return Response в нужном формате
     */
    HouseResponse toHouseResponse(House house);

    /**
     * Сливает существующий дом с информацией из Request
     *
     * @param house    существующий дом
     * @param houseDto информация для обновления
     * @return обновлённый дом
     */
    House update(@MappingTarget House house, HouseRequest houseDto);
}