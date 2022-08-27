package org.example.ch4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.StringJoiner;

@Entity(name = "Email")
public class Email {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String subject;

    @OneToOne
    private Message message;

    public Email() {
        // No-op.
    }

    public Email(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Email.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subject='" + subject + "'")
                .add("message.content=" + (message == null ? "" : message.getContent()))
                .toString();
    }
}
