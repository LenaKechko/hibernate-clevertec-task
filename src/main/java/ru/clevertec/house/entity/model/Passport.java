
package ru.clevertec.house.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * Модель для паспорта, поля в союзе являются уникальными
 */
@Data
@Embeddable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"passport_series", "passport_number"}))
public class Passport {

    /**
     * Серия паспорта
     */
    @Column(name = "passport_series")
    private String passportSeries;

    /**
     * Номер паспорта
     */
    @Column(name = "passport_number")
    private String passportNumber;
}
