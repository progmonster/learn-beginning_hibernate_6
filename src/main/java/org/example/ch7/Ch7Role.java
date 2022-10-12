package org.example.ch7;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
public class Ch7Role {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    public Ch7Role(String title) {
        this.title = title;
    }
}
