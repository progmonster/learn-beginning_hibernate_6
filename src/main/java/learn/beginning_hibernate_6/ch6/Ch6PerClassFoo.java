package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6PerClassFoo extends Ch6PerClassBase {
    private String foo;

    public Ch6PerClassFoo() {
    }

    public Ch6PerClassFoo(Long id, String base, String foo) {
        super(id, base);
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
