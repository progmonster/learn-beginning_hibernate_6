package org.example.ch6;

import jakarta.persistence.Entity;

@Entity
public class Ch6PerClassBoo extends Ch6PerClassBase {
    private String boo;

    public Ch6PerClassBoo() {
    }

    public Ch6PerClassBoo(Long id, String base, String boo) {
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
