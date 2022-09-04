package org.example.ch4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity(name = "Email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @OneToMany(mappedBy = "email", orphanRemoval = true)
    private Set<Message> messages = new HashSet<Message>();

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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Email.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subject='" + subject + "'")
                .add("message.content=" + (messages == null ? "" : messages.size()))
                .toString();
    }
}
