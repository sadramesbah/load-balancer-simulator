package com.sadramesbah.load_balancer_simulator.controller;

import com.sadramesbah.load_balancer_simulator.model.Task;
import com.sadramesbah.load_balancer_simulator.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    for (int i = 0; i < 50; i++) {
      tasks.add(new Task());
    }

    for (Task task : tasks) {
      serverService.handleTaskInServer(task);
      try {
        Thread.sleep(1000); // 1-second delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("Task execution interrupted", e);
      }
      model.addAttribute("servers", serverService.getAllServers().values());
    }

    return "server_stats";
  }
}
