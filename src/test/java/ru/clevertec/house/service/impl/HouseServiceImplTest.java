package ru.clevertec.house.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonWithoutLiveHouseResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.exception.EntityNotFoundException;
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

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private IRepository<House> houseRepository;

    @Mock
    private IRepository<Person> personRepository;

    @Mock
    private HouseMapper houseMapper;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private HouseServiceImpl service;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllShouldReturnListHouseResponse() {
        // given
        List<HouseResponse> expectedList = new ArrayList<>();// testData.buildListAnimalsDto();
        List<House> houseList = new ArrayList<>();

        when(houseRepository.findAll()).thenReturn(houseList);

        IntStream.range(0, houseList.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(houseMapper).toHouseResponse(houseList.get(index)));

        // when
        List<HouseResponse> actualList = service.getAll();

        // then
        assertEquals(expectedList, actualList);
    }

    @Test
    void getShouldReturnHouseResponse() {
        // given

        UUID uuid = UUID.randomUUID();//testData.getUuid();
        HouseResponse expected = new HouseResponse();//testData.buildAnimalDto();
        House houseFromDB = new House();//testData.buildAnimal();

        doReturn(Optional.of(houseFromDB))
                .when(houseRepository)
                .findByUuid(uuid);

        doReturn(expected)
                .when(houseMapper)
                .toHouseResponse(houseFromDB);

        // when
        HouseResponse actual = service.get(uuid);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.area, expected.getArea())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.country, expected.getCountry())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.city, expected.getCity())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.street, expected.getStreet())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.numberHouse, expected.getNumberHouse());
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
        HouseRequest houseToSave = new HouseRequest();//testData.buildAnimalDto();
        House expectedHouse = new House();//testData.buildAnimal();
        UUID expectedUuid = UUID.randomUUID();//testData.getUuid();

        when(houseMapper.toHouse(houseToSave))
                .thenReturn(expectedHouse);

        when(houseRepository.save(expectedHouse)).thenReturn(expectedUuid);

        // when
        UUID actualUuid = service.create(houseToSave);

        // then
        assertNotNull(actualUuid);

    }

    @Test
    void update() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();
        House houseToUpdate = new House();//testData.buildAnimal();
        HouseRequest houseRequestToUpdate = new HouseRequest();//testData.buildAnimalDto();
        houseRequestToUpdate.setArea(10.5);
        House expected = new House();//testData.buldAnimal();
        expected.setArea(10.5);

        doReturn(expected)
                .when(houseMapper)
                .update(houseToUpdate, houseRequestToUpdate);

        doReturn(Optional.of(houseToUpdate))
                .when(houseRepository)
                .findByUuid(uuid);

        // when
        service.update(uuid, houseRequestToUpdate);

        // then
        verify(houseRepository).update(expected);
    }

    @Test
    void delete() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();

        // when
        service.delete(uuid);

        // then
        verify(houseRepository).delete(uuid);

    }

    @Test
    void getPersonsLivingInHouse() {
        // given
        UUID uuid = UUID.randomUUID();//testData.getUuid();
        House houseToGet = new House();//testData.buildAnimal();
        List<PersonWithoutLiveHouseResponse> expectedList = new ArrayList<>();
        List<Person> residents = houseToGet.getResidents();

        doReturn(Optional.of(houseToGet))
                .when(houseRepository)
                .findByUuid(uuid);

        IntStream.range(0, residents.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(personMapper)
                                .toPersonWithoutLiveHouseResponse(residents.get(index)));

        // when
        List<PersonWithoutLiveHouseResponse> actualList = service.getPersonsLivingInHouse(uuid);

        // then
        assertEquals(expectedList, actualList);
    }
}