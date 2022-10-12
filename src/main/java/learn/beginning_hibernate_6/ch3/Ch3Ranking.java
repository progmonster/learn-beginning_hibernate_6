package learn.beginning_hibernate_6.ch3;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Ch3Ranking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Ch3Person subject;

    @ManyToOne
    private Ch3Person observer;

    @ManyToOne
    private Ch3Skill skill;

    @Column
    private int ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ch3Person getSubject() {
        return subject;
    }

    public void setSubject(Ch3Person subject) {
        this.subject = subject;
    }

    public Ch3Person getObserver() {
        return observer;
    }

    public void setObserver(Ch3Person observer) {
        this.observer = observer;
    }

    public Ch3Skill getSkill() {
        return skill;
    }

    public void setSkill(Ch3Skill skill) {
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
        Ch3Ranking ranking1 = (Ch3Ranking) o;
        return ranking == ranking1.ranking && Objects.equals(id, ranking1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ranking);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ch3Ranking.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subject=" + subject)
                .add("observer=" + observer)
                .add("skill=" + skill)
                .add("ranking=" + ranking)
                .toString();
    }
}
