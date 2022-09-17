package org.example.ch4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Ch4SimpleObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "`key`")
    private String key;

    @Column(name = "`value`")
    private Integer value;

    public Ch4SimpleObject() {
        // No-op.
    }

    public Ch4SimpleObject(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ch4SimpleObject that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKey(), getValue());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch4SimpleObject.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("key='" + key + "'")
                .add("value=" + value)
                .toString();
    }
}
