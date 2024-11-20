package com.sadramesbah.load_balancer_simulator.controller;

import com.sadramesbah.load_balancer_simulator.model.Task;
import com.sadramesbah.load_balancer_simulator.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.Instant;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ServerController {

  @Autowired
  private ServerService serverService;

  @GetMapping("/server-stats")
  public String getServerStats(Model model) {
    model.addAttribute("servers", serverService.getAllServers().values());
    return "server_stats";
  }

  @GetMapping("/start-tasks")
  public String startTasks(Model model) {
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      tasks.add(new Task());
    }

    for (Task task : tasks) {
      serverService.handleTaskInServer(task);
      try {
        Thread.sleep(100); // half a second delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("Task execution interrupted", e);
      }

      // check if elapsedTime is greater than requiredTime
      if (task.getElapsedTime(Instant.now()).toMillis() > task.getTimeRequiredInMilliseconds()) {
        serverService.finishTaskInServer(task.getAssignedServerId(), task);
      }

      model.addAttribute("servers", serverService.getAllServers().values());
    }

    return "server_stats";
  }
}
