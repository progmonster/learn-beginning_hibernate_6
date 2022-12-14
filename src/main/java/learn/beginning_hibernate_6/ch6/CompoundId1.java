package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Embeddable
public class CompoundId1 implements Serializable {
    private String key1;

    private String key2;

    public CompoundId1() {
    }

    public CompoundId1(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompoundId1 that)) return false;
        return Objects.equals(getKey1(), that.getKey1()) && Objects.equals(getKey2(), that.getKey2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey1(), getKey2());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CompoundId1.class.getSimpleName() + "[", "]")
                .add("key1='" + key1 + "'")
                .add("key2='" + key2 + "'")
                .toString();
    }
}
