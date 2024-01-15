package ru.clevertec.house.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.exception.ValidateException;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.house.entity.model.Sex.FEMALE;

class PersonServiceImplTest {

    @Mock
    private PersonMapper personMapper;

    @Mock
    private HouseMapper houseMapper;

    @Mock
    private IRepository<Person> personRepository;

    @Mock
    private IRepository<House> houseRepository;

    @InjectMocks
    private PersonServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllShouldReturnListPersonResponse() {
        // given
        List<PersonResponse> expectedList = new ArrayList<>();// testData.buildListAnimalsDto();
        List<Person> personList = new ArrayList<>();

        when(personRepository.findAll()).thenReturn(personList);

        IntStream.range(0, personList.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(personMapper).toPersonResponse(personList.get(index)));

        // when
        List<PersonResponse> actualList = service.getAll();

        // then
        assertEquals(expectedList, actualList);

    }

    @Test
    void getShouldReturnPersonResponse() {
        // given

        UUID uuid = UUID.randomUUID();//testData.getUuid();
        PersonResponse expected = new PersonResponse();//testData.buildAnimalDto();
        Person personFromDB = new Person();//testData.buildAnimal();

        doReturn(Optional.of(personFromDB))
                .when(personRepository)
                .findByUuid(uuid);

        doReturn(expected)
                .when(personMapper)
                .toPersonResponse(personFromDB);

        // when
        PersonResponse actual = service.get(uuid);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.surname, expected.getSurname())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.sex, expected.getSex())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.passport, expected.getPassport())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.createDate, expected.getCreateDate())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.updateDate, expected.getUpdateDate())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.liveHouse, expected.getLiveHouse());
    }

    @Test
    void getShouldReturnExceptionByNotExistUuid() {
        // given
        UUID uuid = UUID.randomUUID();

        // when-then
        assertThrows(EntityNotFoundException.class, () -> service.get(uuid));
    }

    @Test
    void createShouldReturnUuidWhenHouseSave() {
        // given
        PersonRequest personToSave = new PersonRequest();//testData.buildAnimalDto();
        Person expectedPerson = new Person();//testData.buildAnimal();
        UUID expectedUuid = UUID.randomUUID();//testData.getUuid();

        when(personMapper.toPerson(personToSave))
                .thenReturn(expectedPerson);

        when(personRepository.save(expectedPerson))
                .thenReturn(expectedUuid);

        // when
        UUID actualUuid = service.create(personToSave);

        // then
        assertNotNull(actualUuid);
    }

    @Test
    void updateShouldReturnResponse_OK() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();
        Person personToUpdate = new Person();//testData.buildAnimal();
        PersonRequest personRequestToUpdate = new PersonRequest();//testData.buildAnimalDto();
        personRequestToUpdate.setSex(FEMALE);
        Person expected = new Person(); //testData.buldAnimal();
        expected.setSex(FEMALE);

        doReturn(expected)
                .when(personMapper)
                .update(personToUpdate, personRequestToUpdate);

        doReturn(Optional.of(personToUpdate))
                .when(personRepository)
                .findByUuid(uuid);

        // when
        service.update(uuid, personRequestToUpdate);

        // then
        verify(personRepository).update(expected);
    }

    @Test
    void updateShouldReturnException() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();
        PersonRequest personRequestToUpdate = new PersonRequest();//testData.buildAnimalDto();
        UUID uuidHouseToUpdate = UUID.randomUUID();
        personRequestToUpdate.setUuidHouse(uuidHouseToUpdate);

        // when-then
        assertThrows(ValidateException.class, () -> service.update(uuid, personRequestToUpdate));
    }

    @Test
    void delete() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();

        // when
        service.delete(uuid);

        // then
        verify(personRepository).delete(uuid);
    }

    @Test
    void getOwningHouses() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();
        Person personToGet = new Person();//testData.buildAnimal();
        List<HouseResponse> expectedList = new ArrayList<>();
        List<House> owners = personToGet.getOwnHouses();

        doReturn(Optional.of(personToGet))
                .when(personRepository)
                .findByUuid(uuid);

        IntStream.range(0, owners.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(houseMapper)
                                .toHouseResponse(owners.get(index)));

        // when
        List<HouseResponse> actualList = service.getOwningHouses(uuid);

        // then
        assertEquals(expectedList, actualList);
    }
}