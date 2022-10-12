package learn.beginning_hibernate_6.ch5;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.StringJoiner;

@Embeddable
public class Address {
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("street='" + street + "'")
                .toString();
    }
}
