package ru.clevertec.house.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.controller.IController;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonWithoutLiveHouseResponse;
import ru.clevertec.house.exception.BadClientRequestException;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
public class HouseController implements IController<HouseResponse, HouseRequest> {

    @Autowired
    private HouseService<HouseResponse, HouseRequest> service;

    @GetMapping
    public ResponseEntity<List<HouseResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponse> get(@PathVariable("uuid") UUID uuid) {
        try {
            return ResponseEntity.ok(service.get(uuid));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (BadClientRequestException e) {
            throw new BadClientRequestException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody HouseRequest entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @PutMapping("/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @RequestBody HouseRequest entity) {
        try {
            service.update(uuid, entity);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        try {
            service.delete(uuid);
        } catch (BadClientRequestException e) {
            throw new BadClientRequestException(e.getMessage());
        }
    }

    @GetMapping("/{uuid}/residents")
    public ResponseEntity<List<PersonWithoutLiveHouseResponse>> getPersonsLivingInHouse(@PathVariable("uuid") UUID uuid) {
        try {
            return ResponseEntity.ok(service.getPersonsLivingInHouse(uuid));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
