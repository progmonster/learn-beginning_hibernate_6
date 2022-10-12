package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Ch6BasicMappingEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Integer nullableIntValue;

    private int intValue;

    private String stringValue;

    @Basic(optional = false)
    private SimpleValue simpleValue;

    @Transient
    private String privateKey;

    public Ch6BasicMappingEntity() {
    }

    public Ch6BasicMappingEntity(Long id, Integer nullableIntValue, int intValue, String stringValue, SimpleValue simpleValue, String privateKey) {
        this.id = id;
        this.nullableIntValue = nullableIntValue;
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.simpleValue = simpleValue;
        this.privateKey = privateKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNullableIntValue() {
        return nullableIntValue;
    }

    public void setNullableIntValue(Integer nullableIntValue) {
        this.nullableIntValue = nullableIntValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public SimpleValue getSimpleValue() {
        return simpleValue;
    }

    public void setSimpleValue(SimpleValue simpleValue) {
        this.simpleValue = simpleValue;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ch6BasicMappingEntity that)) return false;
        return getIntValue() == that.getIntValue() && Objects.equals(getId(), that.getId()) && Objects.equals(getNullableIntValue(), that.getNullableIntValue()) && Objects.equals(getStringValue(), that.getStringValue()) && Objects.equals(getSimpleValue(), that.getSimpleValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNullableIntValue(), getIntValue(), getStringValue(), getSimpleValue());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch6BasicMappingEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nullableIntValue=" + nullableIntValue)
                .add("intValue=" + intValue)
                .add("stringValue='" + stringValue + "'")
                .add("simpleValue=" + simpleValue)
                .toString();
    }
}

class SimpleValue implements Serializable {
    private String value;

    public SimpleValue() {
    }

    public SimpleValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleValue that)) return false;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SimpleValue.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .toString();
    }
}
