package ru.clevertec.house.service;

import java.util.List;
import java.util.UUID;

public interface IService<T, K> {

    /**
     * Возвращает все существующий сущности
     *
     * @return лист с информацией о сущностях
     */
    List<T> getAll();

    /**
     * ищет сущность по идентификатору
     *
     * @param uuid идентификатор сущности
     * @return найденная сущность
     * @throws ru.clevertec.house.exception.EntityNotFoundException если не найден
     */
    T get(UUID uuid);

    /**
     * Создаёт новую сущность из Request
     *
     * @param entity объект Request с информацией о создании
     * @return идентификатор созданной сущности
     * @throws ru.clevertec.house.exception.EntityNotFoundException если не найден
     */
    UUID create(K entity);

    /**
     * Обновляет уже существуюую сущность из информации полученной в Request
     *
     * @param uuid   идентификатор сущности для обновления
     * @param entity Request с информацией об обновлении
     * @throws ru.clevertec.house.exception.EntityNotFoundException если не найден
     */
    void update(UUID uuid, K entity);

    /**
     * Удаляет существующую сущность
     *
     * @param uuid идентификатор сущности для удаления
     * @throws ru.clevertec.house.exception.EntityNotFoundException если не найден
     */
    void delete(UUID uuid);

}
