package org.example.ch6;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Ch6DerivedEntity extends Ch6AbstractEntity {
    @Basic(optional = false)
    private String title;

    public Ch6DerivedEntity(String title) {
        this.title = title;
    }
}
