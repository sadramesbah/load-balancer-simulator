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
  private final AtomicInteger roundRobinIndex = new AtomicInteger(0);
  private final Map<Integer, Server> servers;

  public ServerService() {
    this.servers = new HashMap<>();
    // servers initialization
    this.servers.put(1, new Server(1, 8, 4, 16000));
    this.servers.put(2, new Server(2, 8, 4, 16000));
    this.servers.put(3, new Server(3, 12, 6, 32000));
    this.servers.put(4, new Server(4, 12, 6, 32000));
    logger.info("ServerService initialized with {} servers.", servers.size());
  }

  public Optional<Server> getServer(int serverId) {
    return Optional.ofNullable(servers.get(serverId))
        .map(server -> {
          logger.info("Retrieved server {}", serverId);
          return server;
        })
        .or(() -> {
          logger.error("Server with ID {} not found.", serverId);
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
          logger.error("Server {} not found. Cannot handle the task.", nextServerId);
          throw new IllegalArgumentException("Server with ID " + nextServerId + " not found.");
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
          logger.error("Server with ID {} not found. Cannot finish task.", serverId);
          throw new IllegalArgumentException("Server with ID " + serverId + " not found.");
        }
    );
  }

  // returns all servers
  public Map<Integer, Server> getAllServers() {
    return servers;
  }
}