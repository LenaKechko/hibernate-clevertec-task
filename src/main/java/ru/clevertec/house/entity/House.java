package ru.clevertec.house.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс определяющий House
 */

@Data
@Entity
@NoArgsConstructor
@FieldNameConstants
@Table(name = "houses")
@ToString(exclude = {"residents", "owners"})
public class House {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Уникальный UUID
     */
    @Column
    @UuidGenerator
    private UUID uuid;

    /**
     * Площадь дома (в кв.м.)
     */
    @Column
    @NotNull
    private Double area;

    /**
     * Страна
     */
    @Column
    @NotNull
    private String country;

    /**
     * Город
     */
    @Column
    @NotNull
    private String city;

    /**
     * Улица
     */
    @Column
    @NotNull
    private String street;

    /**
     * Номер дома
     */
    @Column(name = "number")
    @NotNull
    private Integer numberHouse;

    /**
     * Дата создания. Не обновляется, создается единожды
     */
    @Column(name = "create_date", insertable = true, updatable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    /**
     * Список жителей
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "liveHouse", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Person> residents;

    /**
     * Список владельцев
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "owners",
            joinColumns = @JoinColumn(name = "id_house"),
            inverseJoinColumns = @JoinColumn(name = "id_person"))
    private List<Person> owners;

    /**
     * Метод для добавления нового жителя в дом
     */
    public void addResidentToHouse(Person person) {
        if (residents == null) {
            residents = new ArrayList<>();
        }
        residents.add(person);
    }

    /**
     * Метод для добавления новых жителей в дом
     */
    public void addResidentToHouse(List<Person> person) {
        if (residents == null) {
            residents = new ArrayList<>();
        }
        residents.addAll(person);
    }

    /**
     * Метод для добавления нового владельца дом
     */
    public void addOwnerToHouse(Person person) {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        owners.add(person);
    }

    /**
     * Метод для добавления новых владельцев дома
     */
    public void addOwnerToHouse(List<Person> person) {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        owners.addAll(person);
    }


}
