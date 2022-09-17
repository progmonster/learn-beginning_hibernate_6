package org.example.ch5;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Ch5ComplexIndexEntity2 {
    @EmbeddedId
    private CompoundId2 id;

    @Column
    private String content;

    public Ch5ComplexIndexEntity2() {
    }

    public Ch5ComplexIndexEntity2(CompoundId2 id, String content) {
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
