package ru.clevertec.house.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.house.dto.response.PersonWithoutLiveHouseResponse;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.PersonResponse;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    /**
     * Маппит Request в сущность без id и UUID
     * Дата создания создается по умолчанию
     *
     * @param personDto - DTO для маппинга
     * @return новый человек
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "liveHouse", ignore = true)
    Person toPerson(PersonRequest personDto);

    /**
     * Маппит текущий Person в Response
     *
     * @param person - существующий человек
     * @return Response в нужном формате
     */
    PersonResponse toPersonResponse(Person person);

    /**
     * Маппит текущий Person в Response без информации о проживании человека
     *
     * @param person - существующий человек
     * @return Response в нужном формате
     */
    PersonWithoutLiveHouseResponse toPersonWithoutLiveHouseResponse(Person person);

    /**
     * Сливает существующего человека с информацией из Request
     *
     * @param person    существующий человек
     * @param personDto информация для обновления
     * @return обновлённый человек
     */
    Person update(@MappingTarget Person person, PersonRequest personDto);
}