package org.example.ch3;

import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.IntSummaryStatistics;
import java.util.Map;

import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toMap;
import static org.example.DatabaseUtils.openSession;

public class HibernateRankingService implements RankingService {
    @Override
    public void addRanking(String subjectName, String observerName, String skillName, int rank) {
        try (Session session = openSession()) {
            session.beginTransaction();

            addRanking(session, subjectName, observerName, skillName, rank);

            session.getTransaction().commit();
        }
    }

    @Override
    public int getAverage(String subject, String skill) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Query<Ranking> query = session
                    .createQuery(
                            "from Ranking r where r.subject.name = :subjectName and r.skill.name = :skillName", Ranking.class
                    );

            query.setParameter("subjectName", subject);
            query.setParameter("skillName", skill);

            IntSummaryStatistics stats = query
                    .list()
                    .stream()
                    .collect(summarizingInt(Ranking::getRanking));

            session.getTransaction().commit();

            return (int) stats.getAverage();
        }
    }

    @Override
    public void changeRanking(String subjectName, String observerName, String skillName, int rank) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Ranking ranking = findRanking(session, subjectName, observerName, skillName);

            if (ranking == null) {
                addRanking(session, subjectName, observerName, skillName, rank);
            } else {
                ranking.setRanking(rank);
            }

            session.getTransaction().commit();
        }

    }

    @Override
    public void removeRanking(String subjectName, String observerName, String skillName) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Ranking ranking = findRanking(session, subjectName, observerName, skillName);

            if (ranking != null) {
                session.remove(ranking);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public Map<String, Integer> getAllSubjectRankingAverages(String subjectName) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Query<Tuple> query = session.createQuery(
                    "select r.skill.name as skillName, avg(r.ranking) as rank from Ranking r" +
                            " where r.subject.name = :subjectName group by r.skill", Tuple.class
            );

            query.setParameter("subjectName", subjectName);

            Map<String, Integer> averages = query
                    .getResultStream()
                    .collect(toMap(
                            t -> t.get("skillName", String.class),
                            t -> t.get("rank", Double.class).intValue()
                    ));

            session.getTransaction().commit();

            return averages;
        }
    }

    @Override
    public Person findBestPersonBySkill(String skillName) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Query<Person> query = session.createQuery(
                    "select r.subject from Ranking r" +
                            " where r.skill.name = :skillName" +
                            " group by r.subject" +
                            " order by avg(r.ranking) desc",
                    Person.class
            );

            query.setParameter("skillName", skillName);
            query.setMaxResults(1);

            Person bestPerson = query.uniqueResult();

            session.getTransaction().commit();

            return bestPerson;
        }
    }

    private static Person findPerson(Session session, String name) {
        Query<Person> query = session.createQuery("from Person p where p.name = :name", Person.class);

        query.setParameter("name", name);

        return query.uniqueResult();
    }

    private static Person savePerson(Session session, String name) {
        Person person = findPerson(session, name);

        if (person == null) {
            person = new Person();

            person.setName(name);

            session.persist(person);
        }

        return person;
    }

    private static Skill findSkill(Session session, String name) {
        Query<Skill> query = session.createQuery("from Skill p where p.name = :name", Skill.class);

        query.setParameter("name", name);

        return query.uniqueResult();
    }

    private static Skill saveSkill(Session session, String name) {
        Skill skill = findSkill(session, name);

        if (skill == null) {
            skill = new Skill();

            skill.setName(name);

            session.persist(skill);
        }

        return skill;
    }

    private static void addRanking(Session session, String subjectName, String observerName, String skillName, int rank) {
        Person observer = savePerson(session, observerName);

        Person subject = savePerson(session, subjectName);

        Skill skill = saveSkill(session, skillName);

        Ranking ranking = new Ranking();

        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);

        session.persist(ranking);
    }

    private static Ranking findRanking(Session session, String subjectName, String observerName, String skill) {
        Query<Ranking> query = session
                .createQuery(
                        "from Ranking r where" +
                                " r.subject.name = :subjectName" +
                                " and r.observer.name = :observerName" +
                                " and r.skill.name = :skillName",
                        Ranking.class
                );

        query.setParameter("subjectName", subjectName);
        query.setParameter("observerName", observerName);
        query.setParameter("skillName", skill);

        return query.uniqueResult();
    }
}