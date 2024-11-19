package com.sadramesbah.load_balancer_simulator.service;

import com.sadramesbah.load_balancer_simulator.model.Server;
import com.sadramesbah.load_balancer_simulator.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServerService {

  private static final Logger logger = LogManager.getLogger(ServerService.class);
  private Map<Integer, Server> servers;

  public ServerService() {
    this.servers = new HashMap<>();
    // servers initialization
    this.servers.put(1, new Server(4, 2, 8));
    this.servers.put(2, new Server(8, 4, 16));
    this.servers.put(3, new Server(12, 6, 32));
    logger.info("ServerService initialized with 3 servers.");
  }

  public Server getServer(int serverId) {
    Server server = servers.get(serverId);
    if (server != null) {
      logger.info("Retrieved server with ID: {}", serverId);
    } else {
      logger.error("Server with ID {} not found.", serverId);
    }
    return server;
  }

  public void handleTaskInServer(int serverId, Task task) {
    Server server = servers.get(serverId);
    if (server != null) {
      server.handleTask(task);
      logger.info("Task handled by server with ID: {}", serverId);
    } else {
      logger.error("Server with ID {} not found. Cannot handle task.", serverId);
      throw new IllegalArgumentException("Server with ID " + serverId + " not found.");
    }
  }

  public void finishTaskInServer(int serverId, Task task) {
    Server server = servers.get(serverId);
    if (server != null) {
      server.finishTask(task);
      logger.info("Task finished by server with ID: {}", serverId);
    } else {
      logger.error("Server with ID {} not found. Cannot finish task.", serverId);
      throw new IllegalArgumentException("Server with ID " + serverId + " not found.");
    }
  }
}