package com.sadramesbah.load_balancer_simulator.model;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

  @Test
  void testStartTask() {
    Task task = new Task();
    assertNotNull(task);
    task.startTask();
    assertNotNull(task.getStartTime());
  }

  @Test
  void testTaskInitialization() {
    Task task = new Task(2, 1, 750, 1000);
    assertNotNull(task);
    assertEquals(2, task.getHighPerformanceCoresRequired());
    assertEquals(1, task.getLowPerformanceCoresRequired());
    assertEquals(750, task.getRamRequiredInMegabytes());
    assertEquals(1000, task.getTimeRequiredInMilliseconds());
    assertEquals(-1, task.getAssignedServerId());
  }

  @Test
  void testRandomTaskInitialization() {
    Task task = new Task();
    assertNotNull(task);
    assertTrue(task.getHighPerformanceCoresRequired() >= 0 &&
        task.getHighPerformanceCoresRequired() <= 1);
    assertTrue(task.getLowPerformanceCoresRequired() >= 1 &&
        task.getLowPerformanceCoresRequired() <= 2);
    assertTrue(task.getRamRequiredInMegabytes() >= 50 &&
        task.getRamRequiredInMegabytes() <= 2000);
    assertTrue(task.getTimeRequiredInMilliseconds() >= 1000 &&
        task.getTimeRequiredInMilliseconds() <= 30000);
    assertEquals(-1, task.getAssignedServerId());
  }

  @Test
  void testSettersAndGetters() {
    Task task = new Task();
    assertNotNull(task);
    task.setHighPerformanceCoresRequired(3);
    task.setLowPerformanceCoresRequired(2);
    task.setRamRequiredInMegabytes(1500);
    task.setTimeRequiredInMilliseconds(2000);
    task.setAssignedServerId(4);
    task.setAssignedHighPerformanceCores(2);
    task.setAssignedLowPerformanceCores(1);

    assertEquals(3, task.getHighPerformanceCoresRequired());
    assertEquals(2, task.getLowPerformanceCoresRequired());
    assertEquals(1500, task.getRamRequiredInMegabytes());
    assertEquals(2000, task.getTimeRequiredInMilliseconds());
    assertEquals(4, task.getAssignedServerId());
    assertEquals(2, task.getAssignedHighPerformanceCores());
    assertEquals(1, task.getAssignedLowPerformanceCores());
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
}