package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Ch6SingleBase {
    @Id
    @GeneratedValue
    private Long id;

    private String base;

    public Ch6SingleBase() {
    }

    protected Ch6SingleBase(Long id, String base) {
        this.id = id;
        this.base = base;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
