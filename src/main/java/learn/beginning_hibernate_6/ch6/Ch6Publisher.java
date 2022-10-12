package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ch6Publisher {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    @OrderBy("title ASC")
    @JoinTable(
            joinColumns = {@JoinColumn(name="publisherId")},
            inverseJoinColumns = {@JoinColumn(name="bookId")}
    )
    private List<Ch6Book> books;
}
