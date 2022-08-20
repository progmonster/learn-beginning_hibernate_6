package org.example.ch3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.example.DatabaseUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingTest {
    public static final String SMELL = "J. C. Smell";

    public static final String GENE = "Gene Showrama";

    public static final String SCOT = "Scotball Most";

    public static final String DREW = "Drew Lombardo";

    public static final String JAVA_SKILL = "Java";

    public static final String PYTHON_SKILL = "Python";

    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        reinitializeDatabase();

        rankingService = new HibernateRankingService();
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }


    @Test
    void shouldAddRanking() {
        rankingService.addRanking(SMELL, GENE, JAVA_SKILL, 7);

        assertEquals(7, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldGetAverage_wasNotExist() {
        assertEquals(0, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldGetAverage() {
        rankingService.addRanking(SMELL, GENE, JAVA_SKILL, 7);
        rankingService.addRanking(SMELL, SCOT, JAVA_SKILL, 8);
        rankingService.addRanking(SMELL, DREW, JAVA_SKILL, 9);

        assertEquals(8, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldChangeRanking_wasExist() {
        rankingService.addRanking(SMELL, GENE, JAVA_SKILL, 7);
        rankingService.changeRanking(SMELL, GENE, JAVA_SKILL, 8);

        assertEquals(8, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldChangeRanking_wasNotExist() {
        rankingService.changeRanking(SMELL, GENE, JAVA_SKILL, 8);

        assertEquals(8, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldRemoveRanking_wasExist() {
        rankingService.addRanking(SMELL, GENE, JAVA_SKILL, 7);
        rankingService.removeRanking(SMELL, GENE, JAVA_SKILL);

        assertEquals(0, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldRemoveRanking_wasNotExist() {
        rankingService.removeRanking(SMELL, GENE, JAVA_SKILL);

        assertEquals(0, rankingService.getAverage(SMELL, JAVA_SKILL));
    }

    @Test
    void shouldGetAllSubjectRankingAverages() {
        rankingService.addRanking(SMELL, GENE, JAVA_SKILL, 7);
        rankingService.addRanking(SMELL, SCOT, JAVA_SKILL, 8);
        rankingService.addRanking(SMELL, DREW, JAVA_SKILL, 9);

        rankingService.addRanking(SMELL, GENE, PYTHON_SKILL, 9);
        rankingService.addRanking(SMELL, SCOT, PYTHON_SKILL, 10);
        rankingService.addRanking(SMELL, DREW, PYTHON_SKILL, 11);

        assertEquals(
                Map.of(
                        JAVA_SKILL, 8,
                        PYTHON_SKILL, 10
                ),
                rankingService.getAllSubjectRankingAverages(SMELL)
        );
    }

    @Test
    void shouldGetTopRatedSubjectBySkill() {
        rankingService.addRanking(GENE, SCOT, PYTHON_SKILL, 100);
        rankingService.addRanking(GENE, SCOT, JAVA_SKILL, 1);
        rankingService.addRanking(GENE, DREW, JAVA_SKILL, 3);

        rankingService.addRanking(SCOT, GENE, JAVA_SKILL, 5);
        rankingService.addRanking(SCOT, DREW, JAVA_SKILL, 7);

        rankingService.addRanking(DREW, SCOT, JAVA_SKILL, 10);
        rankingService.addRanking(DREW, GENE, JAVA_SKILL, 12);

        assertEquals(
                DREW,
                rankingService.findBestPersonBySkill(JAVA_SKILL).getName()
        );
    }
}
