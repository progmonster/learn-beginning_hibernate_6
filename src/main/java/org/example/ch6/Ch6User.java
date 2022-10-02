package org.example.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@NamedQueries(
        {
                @NamedQuery(name = "findByLastNameAsc", query = "from Ch6User u where u.lastName = :lastName order by u.lastName asc"),
                @NamedQuery(name = "findByLastNameDesc", query = "from Ch6User u where u.lastName = :lastName order by u.lastName desc"),
        }
)
public class Ch6User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    public Ch6User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}