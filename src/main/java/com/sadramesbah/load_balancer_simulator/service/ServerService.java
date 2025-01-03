package com.sadramesbah.load_balancer_simulator.service;

import com.sadramesbah.load_balancer_simulator.model.Server;
import com.sadramesbah.load_balancer_simulator.model.Task;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServerService {

  private static final Logger logger = LogManager.getLogger(ServerService.class);
  private static final String SERVER_WITH_ID = "Server with ID";
  private static final String SERVER_WITH_ID_NOT_FOUND = "Server with ID %d not found.";
  private final AtomicInteger roundRobinIndex = new AtomicInteger(0);
  private final Map<Integer, Server> servers;

  public ServerService() {
    this.servers = new HashMap<>();
    initializeServers();
    logger.info("ServerService initialized with {} servers.", servers.size());
  }

  private void initializeServers() {
    addServer(createNewServer(1, 8, 4, 16000));
    addServer(createNewServer(2, 8, 4, 16000));
    addServer(createNewServer(3, 8, 4, 16000));
    addServer(createNewServer(4, 12, 6, 32000));
    addServer(createNewServer(5, 12, 6, 32000));
    addServer(createNewServer(6, 12, 6, 32000));
  }

  public Server createNewServer(int serverId, int highPerformanceCores, int lowPerformanceCores,
      int ramInMegaBytes) {
    return new Server(serverId, highPerformanceCores, lowPerformanceCores, ramInMegaBytes);
  }

  protected void addServer(Server server) {
    if (servers.containsKey(server.getServerId())) {
      logger.error("{} {} already exists. Cannot add server.", SERVER_WITH_ID,
          server.getServerId());
      throw new IllegalArgumentException(
          "%s %d already exists.".formatted(SERVER_WITH_ID, server.getServerId()));
    } else {
      servers.put(server.getServerId(), server);
      logger.info("Server {} added successfully.", server.getServerId());
    }
  }

  protected void removeServer(Server server) {
    if (servers.containsKey(server.getServerId())) {
      servers.remove(server.getServerId());
      logger.info("Server {} removed successfully.", server.getServerId());
    } else {
      logger.error("{} {} not found. Cannot remove server.", SERVER_WITH_ID, server.getServerId());
      throw new IllegalArgumentException(
          SERVER_WITH_ID_NOT_FOUND.formatted(server.getServerId()));
    }
  }

  public Optional<Server> getServer(int serverId) {
    return Optional.ofNullable(servers.get(serverId))
        .map(server -> {
          logger.info("Retrieved server {}", serverId);
          return server;
        })
        .or(() -> {
          logger.error("{} {} not found.", SERVER_WITH_ID, serverId);
          return Optional.empty();
        });
  }

  // assigns a task to a server based on round-robin algorithm
  public void handleTaskInServer(Task task) {
    int nextServerId = roundRobinIndex.getAndIncrement() % servers.size() + 1;
    Optional<Server> optionalServer = Optional.ofNullable(servers.get(nextServerId));
    optionalServer.ifPresentOrElse(
        server -> {
          boolean handled = server.handleTask(nextServerId, task);
          if (handled) {
            logger.info("Task successfully handled by server {}", nextServerId);
          } else {
            logger.warn("Server {} could not handle the task due to insufficient resources.",
                nextServerId);
          }
        },
        () -> {
          logger.error("{} {} not found. Cannot handle the task.", SERVER_WITH_ID, nextServerId);
          throw new IllegalArgumentException(
              SERVER_WITH_ID_NOT_FOUND.formatted(nextServerId));
        }
    );
  }

  // finishes a task in a server
  public void finishTaskInServer(int serverId, Task task) {
    Optional<Server> optionalServer = Optional.ofNullable(servers.get(serverId));
    optionalServer.ifPresentOrElse(
        server -> {
          server.finishTask(task);
          logger.info("Task finished by server {}", serverId);
        },
        () -> {
          logger.error("{} {} not found. Cannot finish task.", SERVER_WITH_ID, serverId);
          throw new IllegalArgumentException(SERVER_WITH_ID_NOT_FOUND.formatted(serverId));
        }
    );
  }

  // returns all servers
  public Map<Integer, Server> getAllServers() {
    return servers;
  }

  // creates a task with random attributes
  public Task createRandomTask() {
    return new Task();
  }
}