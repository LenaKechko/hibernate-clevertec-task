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
import ru.clevertec.house.dto.request.PersonRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.dto.response.PersonResponse;
import ru.clevertec.house.exception.BadClientRequestException;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController implements IController<PersonResponse, PersonRequest> {

    @Autowired
    private PersonService<PersonResponse, PersonRequest> service;

    @Override
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<PersonResponse> get(@PathVariable("uuid") UUID uuid) {
        try {
            return ResponseEntity.ok(service.get(uuid));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody PersonRequest entity) {
        try {
            return ResponseEntity.ok(service.create(entity));
        } catch (BadClientRequestException e) {
            throw new BadClientRequestException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @RequestBody PersonRequest entity) {
        try {
            service.update(uuid, entity);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        try {
            service.delete(uuid);
        } catch (BadClientRequestException e) {
            throw new BadClientRequestException(e.getMessage());
        }
    }

    @GetMapping("/{uuid}/owners")
    public ResponseEntity<List<HouseResponse>> getOwningHouses(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok(service.getOwningHouses(uuid));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
