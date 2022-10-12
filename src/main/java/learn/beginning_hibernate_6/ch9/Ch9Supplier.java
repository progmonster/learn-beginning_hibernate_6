package learn.beginning_hibernate_6.ch9;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude = "products")
@EqualsAndHashCode(exclude = "products")
public class Ch9Supplier {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int rank;

    @OneToMany(mappedBy="supplier", cascade = ALL, orphanRemoval = true)
    private Set<Ch9Product> products = new HashSet<>();

    public Ch9Supplier(String title) {
        this.title = title;
    }

    public Ch9Supplier(String title, int rank) {
        this.title = title;
        this.rank = rank;
    }
}
