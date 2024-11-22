package com.sadramesbah.load_balancer_simulator.model;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

  @Test
  void testStartTask() {
    Task task = new Task();
    task.startTask();
    assertNotNull(task.getStartTime());
  }

  @Test
  void testGetElapsedTime() {
    Task task = new Task();
    task.startTask();
    Instant startTime = task.getStartTime();
    Instant currentTime = startTime.plus(Duration.ofMillis(1000));
    Duration elapsedTime = task.getElapsedTime(currentTime);
    assertEquals(1000, elapsedTime.toMillis());
  }

  @Test
  void testTaskInitialization() {
    Task task = new Task(2, 1, 512, 1000);
    assertEquals(2, task.getHighPerformanceCoresRequired());
    assertEquals(1, task.getLowPerformanceCoresRequired());
    assertEquals(512, task.getRamRequiredInMegabytes());
    assertEquals(1000, task.getTimeRequiredInMilliseconds());
    assertEquals(-1, task.getAssignedServerId());
  }

  @Test
  void testRandomTaskInitialization() {
    Task task = new Task();
    assertTrue(task.getHighPerformanceCoresRequired() >= 0);
    assertTrue(task.getLowPerformanceCoresRequired() >= 1);
    assertTrue(task.getRamRequiredInMegabytes() >= 50);
    assertTrue(task.getTimeRequiredInMilliseconds() >= 1000);
    assertEquals(-1, task.getAssignedServerId());
  }
}