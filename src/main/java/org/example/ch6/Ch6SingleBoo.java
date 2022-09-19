package org.example.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6SingleBoo extends Ch6SingleBase {
    private String boo;

    public Ch6SingleBoo() {
    }

    public Ch6SingleBoo(Long id, String base, String boo) {
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
