package org.example.ch5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ch5ComplexIndexEntity1 {
    @Id
    private CompoundId1 id;

    @Column
    private String content;

    public Ch5ComplexIndexEntity1() {
    }

    public Ch5ComplexIndexEntity1(CompoundId1 id, String content) {
        this.id = id;
        this.content = content;
    }

    public CompoundId1 getId() {
        return id;
    }

    public void setId(CompoundId1 id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
