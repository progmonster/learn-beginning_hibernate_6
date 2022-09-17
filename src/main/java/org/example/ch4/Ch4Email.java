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

@Entity
public class Ch4Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @OneToMany(mappedBy = "email", orphanRemoval = true)
    private Set<Ch4Message> messages = new HashSet<>();

    public Ch4Email() {
        // No-op.
    }

    public Ch4Email(String subject) {
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

    public Set<Ch4Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Ch4Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch4Email.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subject='" + subject + "'")
                .add("message.content=" + (messages == null ? "" : messages.size()))
                .toString();
    }
}
