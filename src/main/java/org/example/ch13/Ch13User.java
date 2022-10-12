package org.example.ch13;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Data
@Entity
@NoArgsConstructor
public class Ch13User {
    @Id
    @GeneratedValue
    private Long id;

    @Audited
    private String name;

    private int age;

    public Ch13User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
