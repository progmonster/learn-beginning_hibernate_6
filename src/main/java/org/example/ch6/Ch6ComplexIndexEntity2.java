package org.example.ch6;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Ch6ComplexIndexEntity2 {
    @EmbeddedId
    private CompoundId2 id;

    @Column
    private String content;

    public Ch6ComplexIndexEntity2() {
    }

    public Ch6ComplexIndexEntity2(CompoundId2 id, String content) {
        this.id = id;
        this.content = content;
    }

    public CompoundId2 getId() {
        return id;
    }

    public void setId(CompoundId2 id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
