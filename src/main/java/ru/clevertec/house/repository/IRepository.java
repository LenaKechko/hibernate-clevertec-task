package ru.clevertec.house.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<T> {

    /**
     * ищет все продукты в сущности
     *
     * @return список найденных сущностей
     */
    List<T> findAll();

    /**
     * ищет в бд сущность по uuid
     *
     * @param uuid идентификатор продукта
     * @return Optional<T> если найден, иначе exception
     */
    Optional<T> findByUuid(UUID uuid);

    /**
     * Сохраняет сущность в памяти
     *
     * @param entity сохраняемая сущность
     * @return идентификатор
     */
    UUID save(T entity);

    /**
     * Обновляет сущность из памяти по uuid
     *
     * @param entity сущность
     */
    void update(T entity);


    /**
     * Удаляет сущность из памяти по uuid
     *
     * @param uuid идентификатор сущности
     */
    void delete(UUID uuid);

}
