package org.example.ch5;

import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.DatabaseUtils.openSession;
import static org.example.DatabaseUtils.reinitializeDatabase;
import static org.example.DatabaseUtils.uninitializeDatabase;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class LazyTest {
    @BeforeEach
    void setUp() {
        reinitializeDatabase();
    }

    @Test
    void lazyTest_loadingParentEntity() {
        long userId = createAndSaveTestData().getId();

        User loadedUser;

        try(Session session = openSession()) {
            loadedUser = session.get(User.class, userId);
        }

        assertAll(
                () -> assertNotNull(loadedUser.getId()),
                () -> assertNotNull(loadedUser.getName()),
                () -> assertNotNull(loadedUser.getAddress()),
                () -> assertNotNull(loadedUser.getEmail().getEmail()),
                () -> assertThrowsExactly(LazyInitializationException.class, () -> loadedUser.getCards().iterator().next())
        );
    }

    @Test
    void lazyTest_loadingChildEntity() {
        User user = createAndSaveTestData();

        Card card = user.getCards().iterator().next();

        Card loadedCard;

        try(Session session = openSession()) {
            loadedCard = session.get(Card.class, card.getId());
        }

        assertAll(
                () -> assertNotNull(loadedCard.getId()),
                () -> assertNotNull(loadedCard.getNumber()),
                () -> assertNotNull(loadedCard.getUser().getName())
        );
    }

    private static User createAndSaveTestData() {
        Email email = new Email();

        email.setEmail("john@test.com");

        Address address = new Address();

        address.setStreet("Test Street");

        User user = new User();

        user.setName("John");
        user.setEmail(email);
        user.setAddress(address);

        Card card = new Card();
        card.setNumber("123");
        card.setUser(user);
        user.getCards().add(card);

        try(Session session = openSession()) {
            session.getTransaction().begin();

            session.persist(user);

            session.getTransaction().commit();
        }

        return user;
    }

    @AfterEach
    void tearDown() {
        uninitializeDatabase();
    }
}
