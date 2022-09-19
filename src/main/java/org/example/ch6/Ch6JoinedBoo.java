package org.example.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6JoinedBoo extends Ch6JoinedBase {
    private String boo;

    public Ch6JoinedBoo() {
    }

    public Ch6JoinedBoo(Long id, String base, String boo) {
        super(id, base);
        this.boo = boo;
    }

    public String getBoo() {
        return boo;
    }

    public void setBoo(String boo) {
        this.boo = boo;
    }
}
