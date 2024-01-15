package ru.clevertec.house.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonWithoutLiveHouseResponse;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.repository.IRepository;
import ru.clevertec.house.service.HouseService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class HouseServiceImpl implements HouseService<HouseResponse, HouseRequest> {

    @Autowired
    private IRepository<House> houseRepository;

    @Autowired
    private IRepository<Person> personRepository;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private PersonMapper personMapper;

    public List<HouseResponse> getAll() {
        return houseRepository.findAll().stream()
                .map(houseMapper::toHouseResponse)
                .toList();
    }

    @Override
    public HouseResponse get(UUID uuid) {
        return houseRepository.findByUuid(uuid)
                .map(houseMapper::toHouseResponse)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
    }

    @Override
    public UUID create(HouseRequest entity) {
        House houseToSave = houseMapper.toHouse(entity);
        setListOwners(houseToSave, entity);
        return houseRepository.save(houseToSave);
    }

    @Override
    public void update(UUID uuid, HouseRequest entity) {
        House houseToUpdate = houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
        houseToUpdate = houseMapper.update(houseToUpdate, entity);
        setListOwners(houseToUpdate, entity);
        houseRepository.update(houseToUpdate);
    }

    @Override
    public void delete(UUID uuid) {
        houseRepository.delete(uuid);
    }

    public List<PersonWithoutLiveHouseResponse> getPersonsLivingInHouse(UUID uuid) {
        return houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid))
                .getResidents()
                .stream()
                .map(personMapper::toPersonWithoutLiveHouseResponse)
                .toList();
    }

    private void setListOwners(House house, HouseRequest entity) {
        List<UUID> temp = entity.getUuidOwners();
        if (temp != null) {
            house.addOwnerToHouse(getPersons(temp));
        }
    }

    private List<Person> getPersons(List<UUID> entity) {
        return entity.stream()
                .map(personRepository::findByUuid)
                .map(el -> el.orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

}
