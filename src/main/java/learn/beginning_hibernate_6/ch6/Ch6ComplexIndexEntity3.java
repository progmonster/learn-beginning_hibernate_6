package learn.beginning_hibernate_6.ch6;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(CompoundId3.class)
public class Ch6ComplexIndexEntity3 {
    @Id
    private String key1;

    @Id
    private String key2;

    @Column
    private String content;

    public Ch6ComplexIndexEntity3() {
    }

    public Ch6ComplexIndexEntity3(String key1, String key2, String content) {
        this.key1 = key1;
        this.key2 = key2;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
