package org.example.ch9;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Ch9Foo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Version
    private int version;

    public Ch9Foo(String name) {
        this.name = name;
    }
}
