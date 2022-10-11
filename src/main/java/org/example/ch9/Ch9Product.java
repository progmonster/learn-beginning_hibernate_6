package org.example.ch9;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude = "supplier")
@EqualsAndHashCode(exclude = "supplier")
public class Ch9Product {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int price;

    private Instant timestamp = Instant.now();

    @ManyToOne
    private Ch9Supplier supplier;

    public Ch9Product(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
