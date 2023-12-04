package ch.zli.m223;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

// import ch.zli.m223.model.Booking;
// import ch.zli.m223.model.Entry;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("test")
@ApplicationScoped
public class IntegrationTestDataService {

  @Inject
  EntityManager entityManager;

  @Transactional
  void generateTestData(@Observes StartupEvent event) {

    // Categories
    // var projectACategory = new Booking();
    // projectACategory.setTitle("Project A");
    // entityManager.persist(projectACategory);

    // var projectBCategory = new Booking();
    // projectBCategory.setTitle("Project B");
    // entityManager.persist(projectBCategory);

    // var projectCCategory = new Booking();
    // projectCCategory.setTitle("Project C");
    // entityManager.persist(projectCCategory);

    // Entries
    // var firstEntry = new Entry();
    // firstEntry.setBooking(projectACategory);
    // firstEntry.setTags(new HashSet<>(Arrays.asList(programmingTag, debuggingTag)));
    // firstEntry.setCheckIn(LocalDateTime.now().minusHours(3));
    // firstEntry.setCheckOut(LocalDateTime.now().minusHours(2));
    // entityManager.persist(firstEntry);

    // var secondEntry = new Entry();
    // secondEntry.setBooking(projectACategory);
    // secondEntry.setTags(new HashSet<>(Arrays.asList(meetingTag)));
    // secondEntry.setCheckIn(LocalDateTime.now().minusHours(2));
    // secondEntry.setCheckOut(LocalDateTime.now().minusHours(1));
    // entityManager.persist(secondEntry);

    // var thirdEntry = new Entry();
    // thirdEntry.setBooking(projectBCategory);
    // thirdEntry.setTags(new HashSet<>(Arrays.asList(programmingTag)));
    // thirdEntry.setCheckIn(LocalDateTime.now().minusHours(1));
    // thirdEntry.setCheckOut(LocalDateTime.now());
    // entityManager.persist(thirdEntry);
  }
}
