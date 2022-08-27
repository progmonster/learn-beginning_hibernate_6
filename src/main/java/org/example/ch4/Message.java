package org.example.ch4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.StringJoiner;

@Entity(name = "Message")
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String content;

    @OneToOne
    private Email email;

    public Message(String content) {
        this.content = content;
    }

    public Message() {
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("content='" + content + "'")
                .add("email.subject=" + (email == null ? "" : email.getSubject()))
                .toString();
    }
}
