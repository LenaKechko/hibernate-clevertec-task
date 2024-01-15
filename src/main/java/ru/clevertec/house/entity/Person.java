package ru.clevertec.house.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import ru.clevertec.house.entity.model.Passport;
import ru.clevertec.house.entity.model.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс определяющий Person
 */
@Data
@Entity
@NoArgsConstructor
@FieldNameConstants
@Table(name = "persons")
@ToString(exclude = {"liveHouse", "ownHouses"})
public class Person {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Уникальный uuid
     */
    @Column(unique = true)
    @NotNull
    private UUID uuid;

    /**
     * Имя
     */
    @Column
    @NotNull
    private String name;

    /**
     * Фамилия
     */
    @Column
    @NotNull
    private String surname;

    /**
     * Пол
     * Может быть только FEMALE и MALE
     */
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    /**
     * Объект паспорт
     */
    @Embedded
    @NotNull
    private Passport passport;

    /**
     * Дата создания
     * Не обновляется, создается единожды
     */
    @Column(name = "create_date", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    /**
     * Дата обновления
     * Обновляется каждый раз при изменении сущности
     */
    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updateDate;

    /**
     * Дом, в котором живет Person
     * Обязательное поле
     */
    @NotNull
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "id_live_house")
    private House liveHouse;

    /**
     * Список владельцев среди Person
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "owners",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_house"))
    private List<House> ownHouses;

    /**
     * Метод для добавления дома во владении
     */
    public void addHouseToOwnHouse(House house) {
        if (ownHouses == null) {
            ownHouses = new ArrayList<>();
        }
        ownHouses.add(house);
    }

    /**
     * Метод для добавления домов во владении person
     */
    public void addHouseToOwnHouse(List<House> house) {
        if (ownHouses == null) {
            ownHouses = new ArrayList<>();
        }
        ownHouses.addAll(house);
    }
}
