package org.example.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static jakarta.persistence.TemporalType.DATE;
import static jakarta.persistence.TemporalType.TIME;
import static jakarta.persistence.TemporalType.TIMESTAMP;
import static java.lang.System.currentTimeMillis;


@Data
@Entity
public class Ch6TemporalEntity {
    @Id
    @GeneratedValue
    private Long id;

    private java.sql.Date javaSqlDate = new java.sql.Date(currentTimeMillis());

    private java.sql.Time javaSqlTime = new java.sql.Time(currentTimeMillis());

    private java.sql.Timestamp javaSqlTimestamp = new java.sql.Timestamp(currentTimeMillis());

    private java.util.Date javaUtilDateTemporalDefault = new java.util.Date(currentTimeMillis());

    @Temporal(DATE)
    private java.util.Date javaUtilDateTemporalDate = new java.util.Date(currentTimeMillis());

    @Temporal(TIME)
    private java.util.Date javaUtilDateTemporalTime = new java.util.Date(currentTimeMillis());

    @Temporal(TIMESTAMP)
    private java.util.Date javaUtilDateTemporalTimestamp = new java.util.Date(currentTimeMillis());

    private Instant javaTimeInstant = Instant.now();

    @Temporal(DATE)
    private Instant javaTimeInstantTemporalDate = Instant.now();

    private LocalDateTime javaTimeLocalDateTime = LocalDateTime.now();

    private LocalDate javaTimeLocalDate = LocalDate.now();

    private LocalTime javaTimeLocalTime = LocalTime.now();

    private ZonedDateTime javaTimeZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg"));

    private OffsetDateTime javaTimeOffsetDateTime = OffsetDateTime.now(ZoneId.of("Asia/Yekaterinburg"));
}
