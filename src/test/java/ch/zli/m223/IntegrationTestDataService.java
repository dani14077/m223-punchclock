package ch.zli.m223;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Booking;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("test")
@ApplicationScoped
public class IntegrationTestDataService {

  @Inject
  EntityManager entityManager;

  @Transactional
    void generateTestData(@Observes StartupEvent event) {
        // Create test users
        var user1 = new ApplicationUser();
        user1.setFirstName("user1");
        user1.setLastName("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setRole("user");
        entityManager.persist(user1);

        var user2 = new ApplicationUser();
        user2.setFirstName("user2");
        user2.setLastName("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setRole("admin");
        entityManager.persist(user2);

        // Create test bookings
        var booking1 = new Booking();
        booking1.setApplicationUser(user1);
        booking1.setDate(new Date(2023-12-07));
        booking1.setType("Morning");
        booking1.setStatus("Pending");
        entityManager.persist(booking1);

        var booking2 = new Booking();
        booking2.setApplicationUser(user2);
        booking2.setDate(new Date(2023-05-11));
        booking2.setType("Afternoon");
        booking2.setStatus("Accepted");
        entityManager.persist(booking2);
    }
}
