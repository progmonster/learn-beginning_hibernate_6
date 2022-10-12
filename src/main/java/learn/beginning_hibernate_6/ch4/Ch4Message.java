package learn.beginning_hibernate_6.ch4;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.StringJoiner;

@Entity
public class Ch4Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ch4Email email;

    public Ch4Message(String content) {
        this.content = content;
    }

    public Ch4Message() {
        // No-op.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Ch4Email getEmail() {
        return email;
    }

    public void setEmail(Ch4Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch4Message.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("content='" + content + "'")
                .add("email.subject=" + (email == null ? "" : email.getSubject()))
                .toString();
    }
}
