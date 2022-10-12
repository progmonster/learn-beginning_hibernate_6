package learn.beginning_hibernate_6.ch7;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@Data
@Entity
@NoGummyBear
@NoArgsConstructor
public class Ch7User {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @Email
    @NotNull
    private String email;

    @Size(min = 1)
    @OneToMany(fetch = EAGER, cascade = ALL)
    private Set<@Valid Ch7Role> roles = new HashSet<>();

    public Ch7User(String name, String email, String... roles) {
        this.name = name;
        this.email = email;

        this.roles = stream(roles)
                .map(Ch7Role::new)
                .collect(toSet());
    }
}
