package com.sadramesbah.load_balancer_simulator.service;

import com.sadramesbah.load_balancer_simulator.model.Server;
import com.sadramesbah.load_balancer_simulator.model.Task;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerServiceTest {

  private ServerService serverService;
  private Task task1;

  @BeforeEach
  void setUp() {
    serverService = new ServerService();
    task1 = new Task(2, 1, 512, 1000);
  }

  @Test
  void testAddServer() {
    Server server7 = new Server(7, 2, 1, 8000);
    serverService.addServer(server7);
    assertEquals(7, serverService.getAllServers().size());

    Optional<Server> retrievedServer = serverService.getServer(7);
    assertTrue(retrievedServer.isPresent(), "Server should be present");
    retrievedServer.ifPresent(server -> {
      assertEquals(7, server.getServerId());
      assertEquals(2, server.getHighPerformanceCores());
      assertEquals(1, server.getLowPerformanceCores());
      assertEquals(8000, server.getTotalRam());
    });
  }

  @Test
  void testRemoveServer() {
    Server server1 = serverService.getServer(1).orElseThrow();
    serverService.removeServer(server1);
    assertEquals(5, serverService.getAllServers().size());
    Optional<Server> retrievedServer = serverService.getServer(1);
    assertFalse(retrievedServer.isPresent(), "Server should not be present");
  }

  @Test
  void testHandleTaskInServer() {
    serverService.handleTaskInServer(task1);
    assertEquals(1, task1.getAssignedServerId());
    Server server1 = serverService.getServer(1).orElseThrow();
    assertEquals(1, server1.getTasksInProcess().size());
  }

  @Test
  void testHandleTaskInServerWithInsufficientResources() {
    Task largeTask = new Task(10, 10, 40000, 3000);
    serverService.handleTaskInServer(largeTask);
    assertEquals(-1, largeTask.getAssignedServerId());
    Server server1 = serverService.getServer(1).orElseThrow();
    assertEquals(0, server1.getTasksInProcess().size());
  }

  @Test
  void testFinishTaskInServer() {
    serverService.handleTaskInServer(task1);
    assertEquals(1, task1.getAssignedServerId());
    Server server1 = serverService.getServer(1).orElseThrow();
    assertEquals(2, server1.getHighPerformanceCoresInUse());
    assertEquals(1, server1.getLowPerformanceCoresInUse());
    assertEquals(512, server1.getRamInUse());
    serverService.finishTaskInServer(1, task1);
    assertEquals(-1, task1.getAssignedServerId());
    assertEquals(0, server1.getHighPerformanceCoresInUse());
    assertEquals(0, server1.getLowPerformanceCoresInUse());
    assertEquals(0, server1.getRamInUse());
  }

  @Test
  void testGetServerById() {
    assertEquals(6, serverService.getAllServers().size());
    assertTrue(serverService.getServer(1).isPresent());
    assertTrue(serverService.getServer(2).isPresent());
    assertTrue(serverService.getServer(3).isPresent());
    assertTrue(serverService.getServer(4).isPresent());
    assertTrue(serverService.getServer(5).isPresent());
    assertTrue(serverService.getServer(6).isPresent());
  }
}