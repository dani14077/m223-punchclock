package ch.zli.m223.service;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

// import ch.zli.m223.model.Booking;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("dev")
@ApplicationScoped
public class TestDataService {

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

  }
}
