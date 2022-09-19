package org.example.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6JoinedFoo extends Ch6JoinedBase {
    private String foo;

    public Ch6JoinedFoo() {
    }

    public Ch6JoinedFoo(Long id, String base, String foo) {
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
