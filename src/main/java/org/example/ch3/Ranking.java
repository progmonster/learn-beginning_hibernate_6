package org.example.ch3;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person subject;

    @ManyToOne
    private Person observer;

    @ManyToOne
    private Skill skill;

    @Column
    private int ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSubject() {
        return subject;
    }

    public void setSubject(Person subject) {
        this.subject = subject;
    }

    public Person getObserver() {
        return observer;
    }

    public void setObserver(Person observer) {
        this.observer = observer;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ranking ranking1 = (Ranking) o;
        return ranking == ranking1.ranking && Objects.equals(id, ranking1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ranking);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ranking.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subject=" + subject)
                .add("observer=" + observer)
                .add("skill=" + skill)
                .add("ranking=" + ranking)
                .toString();
    }
}
