package org.example.ch5;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
public class Ch5User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @OneToOne(cascade = {PERSIST, REMOVE})
    private Ch5Email email;

    @Embedded
    @AttributeOverrides(
            {@AttributeOverride(name = "street", column = @Column(name = "address_line_1"))}
    )
    private Address address;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = PERSIST)
    private Set<Ch5Card> cards = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ch5Email getEmail() {
        return email;
    }

    public void setEmail(Ch5Email email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Ch5Card> getCards() {
        return cards;
    }

    public void setCards(Set<Ch5Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ch5User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getAddress(), user.getAddress()) && Objects.equals(getCards(), user.getCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getAddress(), getCards());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch5User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("email=" + email)
                .add("address=" + address)
                .add("cards=" + cards)
                .toString();
    }
}
