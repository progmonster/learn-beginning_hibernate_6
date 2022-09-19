package org.example.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6SingleFoo extends Ch6SingleBase {
    private String foo;

    public Ch6SingleFoo() {
    }

    public Ch6SingleFoo(Long id, String base, String foo) {
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
