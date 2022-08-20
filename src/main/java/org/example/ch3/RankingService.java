package org.example.ch3;

import java.util.Map;

public interface RankingService {
    void addRanking(String subjectName, String observerName, String skillName, int rank);

    int getAverage(String subject, String skill);

    void changeRanking(String subjectName, String observerName, String skillName, int rank);

    void removeRanking(String subjectName, String observerName, String skillName);

    Map<String, Integer> getAllSubjectRankingAverages(String subjectName);

    Person findBestPersonBySkill(String skillName);
}
