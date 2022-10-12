package learn.beginning_hibernate_6.ch13;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@NoArgsConstructor
public class Ch13Account {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int amount;

    public Ch13Account(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }
}
