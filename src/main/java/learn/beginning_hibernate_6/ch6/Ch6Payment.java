package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Ch6Payment {
    @Id
    @GeneratedValue
    private Long id;

    private String goal;

    @ManyToOne
    private Ch6Customer customer;

    public Ch6Payment(String goal) {
        this.goal = goal;
    }
}
