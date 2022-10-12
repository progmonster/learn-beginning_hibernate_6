package learn.beginning_hibernate_6.ch3;

import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.IntSummaryStatistics;
import java.util.Map;

import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toMap;
import static learn.beginning_hibernate_6.HibernateUtils.openSession;

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

            Query<Ch3Ranking> query = session
                    .createQuery(
                            "from Ch3Ranking r where r.subject.name = :subjectName and r.skill.name = :skillName", Ch3Ranking.class
                    );

            query.setParameter("subjectName", subject);
            query.setParameter("skillName", skill);

            IntSummaryStatistics stats = query
                    .list()
                    .stream()
                    .collect(summarizingInt(Ch3Ranking::getRanking));

            session.getTransaction().commit();

            return (int) stats.getAverage();
        }
    }

    @Override
    public void changeRanking(String subjectName, String observerName, String skillName, int rank) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Ch3Ranking ranking = findRanking(session, subjectName, observerName, skillName);

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

            Ch3Ranking ranking = findRanking(session, subjectName, observerName, skillName);

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
                    "select r.skill.name as skillName, avg(r.ranking) as rank from Ch3Ranking r" +
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
    public Ch3Person findBestPersonBySkill(String skillName) {
        try (Session session = openSession()) {
            session.beginTransaction();

            Query<Ch3Person> query = session.createQuery(
                    "select r.subject from Ch3Ranking r" +
                            " where r.skill.name = :skillName" +
                            " group by r.subject" +
                            " order by avg(r.ranking) desc",
                    Ch3Person.class
            );

            query.setParameter("skillName", skillName);
            query.setMaxResults(1);

            Ch3Person bestPerson = query.uniqueResult();

            session.getTransaction().commit();

            return bestPerson;
        }
    }

    private static Ch3Person findPerson(Session session, String name) {
        Query<Ch3Person> query = session.createQuery("from Ch3Person p where p.name = :name", Ch3Person.class);

        query.setParameter("name", name);

        return query.uniqueResult();
    }

    private static Ch3Person savePerson(Session session, String name) {
        Ch3Person person = findPerson(session, name);

        if (person == null) {
            person = new Ch3Person();

            person.setName(name);

            session.persist(person);
        }

        return person;
    }

    private static Ch3Skill findSkill(Session session, String name) {
        Query<Ch3Skill> query = session.createQuery("from Ch3Skill p where p.name = :name", Ch3Skill.class);

        query.setParameter("name", name);

        return query.uniqueResult();
    }

    private static Ch3Skill saveSkill(Session session, String name) {
        Ch3Skill skill = findSkill(session, name);

        if (skill == null) {
            skill = new Ch3Skill();

            skill.setName(name);

            session.persist(skill);
        }

        return skill;
    }

    private static void addRanking(Session session, String subjectName, String observerName, String skillName, int rank) {
        Ch3Person observer = savePerson(session, observerName);

        Ch3Person subject = savePerson(session, subjectName);

        Ch3Skill skill = saveSkill(session, skillName);

        Ch3Ranking ranking = new Ch3Ranking();

        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);

        session.persist(ranking);
    }

    private static Ch3Ranking findRanking(Session session, String subjectName, String observerName, String skill) {
        Query<Ch3Ranking> query = session
                .createQuery(
                        "from Ch3Ranking r where" +
                                " r.subject.name = :subjectName" +
                                " and r.observer.name = :observerName" +
                                " and r.skill.name = :skillName",
                        Ch3Ranking.class
                );

        query.setParameter("subjectName", subjectName);
        query.setParameter("observerName", observerName);
        query.setParameter("skillName", skill);

        return query.uniqueResult();
    }
}
