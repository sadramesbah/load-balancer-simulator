package com.sadramesbah.load_balancer_simulator.controller;

import com.sadramesbah.load_balancer_simulator.model.Task;
import com.sadramesbah.load_balancer_simulator.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ServerController {

  private final ServerService serverService;

  @Autowired
  public ServerController(ServerService serverService) {
    this.serverService = serverService;
  }

  @GetMapping("/server-stats")
  public ModelAndView getStats() {
    ModelAndView modelAndView = new ModelAndView("server_stats");
    modelAndView.addObject("servers", serverService.getAllServers().values());
    return modelAndView;
  }

  @PostMapping("/create-task")
  public void createTask() {
    Task newTask = serverService.createRandomTask();
    serverService.handleTaskInServer(newTask);
  }
}