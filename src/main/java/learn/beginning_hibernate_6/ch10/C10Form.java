package learn.beginning_hibernate_6.ch10;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

@Data
@Entity
@NoArgsConstructor
@Filters({@Filter(name = "byActive", condition = "active = :active")})
public class C10Form {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private boolean active;

    public C10Form(String title, boolean active) {
        this.title = title;
        this.active = active;
    }
}
