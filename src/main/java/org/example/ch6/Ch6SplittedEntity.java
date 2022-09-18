package org.example.ch6;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.SecondaryTables;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@SecondaryTables({
        @SecondaryTable(name = "CH_5_SPLITTED_ENITITY_BILLING_DETAILS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "entity_id")),
        @SecondaryTable(name = "CH_5_SPLITTED_ENITITY_ADDRESS_DETAILS"),
})
public class Ch6SplittedEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(table = "CH_5_SPLITTED_ENITITY_BILLING_DETAILS")
    private String billingPlan;

    @Column(table = "CH_5_SPLITTED_ENITITY_ADDRESS_DETAILS")
    private String address;

    public Ch6SplittedEntity() {
    }

    public Ch6SplittedEntity(Long id, String name, String billingPlan, String address) {
        this.id = id;
        this.name = name;
        this.billingPlan = billingPlan;
        this.address = address;
    }

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

    public String getBillingPlan() {
        return billingPlan;
    }

    public void setBillingPlan(String billingPlan) {
        this.billingPlan = billingPlan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ch6SplittedEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getBillingPlan(), that.getBillingPlan()) && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBillingPlan(), getAddress());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch6SplittedEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("billingPlan='" + billingPlan + "'")
                .add("address='" + address + "'")
                .toString();
    }
}
