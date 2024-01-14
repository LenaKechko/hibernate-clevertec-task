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

@Data
@Entity
@NoArgsConstructor
@FieldNameConstants
@Table(name = "houses")
@ToString(exclude = {"residents", "owners"})
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @UuidGenerator
    private UUID uuid;

    @Column
    @NotNull
    private Double area;

    @Column
    @NotNull
    private String country;

    @Column
    @NotNull
    private String city;

    @Column
    @NotNull
    private String street;

    @Column(name = "number")
    @NotNull
    private Integer numberHouse;

    @Column(name = "create_date", insertable = true, updatable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "liveHouse", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Person> residents;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "owners",
            joinColumns = @JoinColumn(name = "id_house"),
            inverseJoinColumns = @JoinColumn(name = "id_person"))
    private List<Person> owners;

    public void addResidentToHouse(Person person) {
        if (residents == null) {
            residents = new ArrayList<>();
        }
        residents.add(person);
    }

    public void addResidentToHouse(List<Person> person) {
        if (residents == null) {
            residents = new ArrayList<>();
        }
        residents.addAll(person);
    }

    public void addOwnerToHouse(Person person) {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        owners.add(person);
    }

    public void addOwnerToHouse(List<Person> person) {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        owners.addAll(person);
    }


}
