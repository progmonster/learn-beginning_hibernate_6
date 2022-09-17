package org.example.ch5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Ch5Email {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ch5Email email1)) return false;
        return Objects.equals(getId(), email1.getId()) && getEmail().equals(email1.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch5Email.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .toString();
    }
}
