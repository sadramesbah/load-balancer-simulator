package com.sadramesbah.load_balancer_simulator.model;

import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

  private Server server;
  private Task task1;
  private Task task2;

  @BeforeEach
  void setUp() {
    // server with 8 high-performance cores, 4 low-performance cores, and 16GB RAM
    server = new Server(1, 8, 4, 16000);
    // task requiring 2 high-performance cores, 1 low-performance core, 512MB RAM and 1 second to complete
    task1 = new Task(2, 1, 512, 1000);
    // task requiring 1 high-performance core, 2 low-performance cores, 1GB RAM and 2 seconds to complete
    task2 = new Task(1, 2, 1024, 2000);
  }

  @Test
  void testCanHandleTask() {
    assertTrue(server.canHandleTask(task1));
    assertTrue(server.canHandleTask(task2));
  }

  @Test
  void testHandleTask() {
    assertTrue(server.handleTask(1, task1));
    assertEquals(2, server.getHighPerformanceCoresInUse());
    assertEquals(1, server.getLowPerformanceCoresInUse());
    assertEquals(512, server.getRamInUse());
    assertEquals(1, task1.getAssignedServerId());
  }

  @Test
  void testHandleTaskWithInsufficientResources() {
    // task requiring more resources than available
    Task largeTask = new Task(10, 5, 20000, 3000);
    assertFalse(server.handleTask(1, largeTask));
  }

  @Test
  void testFinishTask() {
    server.handleTask(1, task1);
    server.finishTask(task1);
    assertEquals(0, server.getHighPerformanceCoresInUse());
    assertEquals(0, server.getLowPerformanceCoresInUse());
    assertEquals(0, server.getRamInUse());
    assertEquals(-1, task1.getAssignedServerId());
  }

  @Test
  void testFinishTaskNotInProcess() {
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> server.finishTask(task1));
    assertEquals("Task is not being processed by this server.", exception.getMessage());
  }

  @Test
  void testCheckTasksElapsedTimeOnSchedule() {
    server.handleTask(1, task1);
    assertEquals(2, server.getHighPerformanceCoresInUse());
    assertEquals(1, server.getLowPerformanceCoresInUse());
    assertEquals(512, server.getRamInUse());
    assertEquals(1, task1.getAssignedServerId());
    // wait for the task to exceed its time limit and be terminated by the server
    Awaitility.await().atMost(1500, TimeUnit.MILLISECONDS)
        .until(() -> task1.getAssignedServerId() == -1);
    assertEquals(0, server.getHighPerformanceCoresInUse());
    assertEquals(0, server.getLowPerformanceCoresInUse());
    assertEquals(0, server.getRamInUse());
    assertEquals(-1, task1.getAssignedServerId());
  }

  @Test
  void testHandleMultipleTasks() {
    assertTrue(server.handleTask(1, task1));
    assertTrue(server.handleTask(1, task2));
    assertEquals(3, server.getHighPerformanceCoresInUse());
    assertEquals(3, server.getLowPerformanceCoresInUse());
    assertEquals(1536, server.getRamInUse());
  }

  @Test
  void testFinishMultipleTasks() {
    server.handleTask(1, task1);
    server.handleTask(1, task2);
    assertEquals(3, server.getHighPerformanceCoresInUse());
    assertEquals(3, server.getLowPerformanceCoresInUse());
    assertEquals(1536, server.getRamInUse());
    server.finishTask(task1);
    assertEquals(1, server.getHighPerformanceCoresInUse());
    assertEquals(2, server.getLowPerformanceCoresInUse());
    assertEquals(1024, server.getRamInUse());
    server.finishTask(task2);
    assertEquals(0, server.getHighPerformanceCoresInUse());
    assertEquals(0, server.getLowPerformanceCoresInUse());
    assertEquals(0, server.getRamInUse());
  }

  @Test
  void testFinishMultipleTasksOnSchedule() {
    server.handleTask(1, task1);
    server.handleTask(1, task2);
    assertEquals(3, server.getHighPerformanceCoresInUse());
    assertEquals(3, server.getLowPerformanceCoresInUse());
    assertEquals(1536, server.getRamInUse());
    // wait for task1 to exceed its time limit and be terminated by the server
    Awaitility.await().atMost(1500, TimeUnit.MILLISECONDS)
        .until(() -> task1.getAssignedServerId() == -1);
    assertEquals(1, server.getHighPerformanceCoresInUse());
    assertEquals(2, server.getLowPerformanceCoresInUse());
    assertEquals(1024, server.getRamInUse());
    // wait for task2 to exceed its time limit and be terminated by the server
    Awaitility.await().atMost(1500, TimeUnit.MILLISECONDS)
        .until(() -> task2.getAssignedServerId() == -1);
    assertEquals(0, server.getHighPerformanceCoresInUse());
    assertEquals(0, server.getLowPerformanceCoresInUse());
    assertEquals(0, server.getRamInUse());
  }

  @Test
  void testHandleTaskWithLowPerformanceCoresAssignedToHighPerformance() {
    Task lowPerformanceTask = new Task(0, 5, 512, 1000);
    assertTrue(server.handleTask(1, lowPerformanceTask));
    // 1 high-performance core used for low-performance task
    assertEquals(1, server.getHighPerformanceCoresInUse());
    assertEquals(4, server.getLowPerformanceCoresInUse());
    assertEquals(512, server.getRamInUse());
    assertEquals(1, lowPerformanceTask.getAssignedServerId());
  }
}