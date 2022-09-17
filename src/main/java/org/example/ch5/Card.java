package org.example.ch5;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return Objects.equals(getId(), card.getId()) && Objects.equals(getUser().getId(), card.getUser().getId()) && Objects.equals(getNumber(), card.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser() == null ? 0 : user == null ? 0 : (getUser().getId() == null ? 0 : getUser().getId()), getNumber());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Card.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("number='" + number + "'")
                .toString();
    }
}
