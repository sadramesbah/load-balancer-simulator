package com.sadramesbah.load_balancer_simulator.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

  private static final Logger logger = LogManager.getLogger(Server.class);

  private int serverId;
  private int highPerformanceCores;
  private int lowPerformanceCores;
  private int totalRamInMegabytes;
  private int highPerformanceCoresInUse;
  private int lowPerformanceCoresInUse;
  private int ramInUseInMegabytes;
  private List<Task> tasksInProcess = new ArrayList<>();

  public Server() {
  }

  public Server(int serverId, int highPerformanceCores, int lowPerformanceCores,
      int totalRamInMegabytes) {
    this.serverId = serverId;
    this.highPerformanceCores = highPerformanceCores;
    this.lowPerformanceCores = lowPerformanceCores;
    this.totalRamInMegabytes = totalRamInMegabytes;
    this.highPerformanceCoresInUse = 0;
    this.lowPerformanceCoresInUse = 0;
    this.ramInUseInMegabytes = 0;
  }

  // getters and setters
  public int getServerId() {
    return serverId;
  }

  public void setServerId(int serverId) {
    this.serverId = serverId;
  }

  public int getHighPerformanceCores() {
    return highPerformanceCores;
  }

  public void setHighPerformanceCores(int highPerformanceCores) {
    this.highPerformanceCores = highPerformanceCores;
  }

  public int getLowPerformanceCores() {
    return lowPerformanceCores;
  }

  public void setLowPerformanceCores(int lowPerformanceCores) {
    this.lowPerformanceCores = lowPerformanceCores;
  }

  public int getTotalRam() {
    return totalRamInMegabytes;
  }

  public void setTotalRam(int totalRam) {
    this.totalRamInMegabytes = totalRam;
  }

  public int getHighPerformanceCoresInUse() {
    return highPerformanceCoresInUse;
  }

  public void setHighPerformanceCoresInUse(int highPerformanceCoresInUse) {
    this.highPerformanceCoresInUse = highPerformanceCoresInUse;
  }

  public int getLowPerformanceCoresInUse() {
    return lowPerformanceCoresInUse;
  }

  public void setLowPerformanceCoresInUse(int lowPerformanceCoresInUse) {
    this.lowPerformanceCoresInUse = lowPerformanceCoresInUse;
  }

  public int getRamInUse() {
    return ramInUseInMegabytes;
  }

  public void setRamInUse(int ramInUse) {
    this.ramInUseInMegabytes = ramInUse;
  }

  public List<Task> getTasksInProcess() {
    return tasksInProcess;
  }

  public void setTasksInProcess(List<Task> tasksInProcess) {
    this.tasksInProcess = tasksInProcess;
  }

  // checks if current instance of server can handle the task
  private boolean canHandleTask(Task task) {
    return
        task.getHighPerformanceCoresRequired() <=
            (highPerformanceCores - highPerformanceCoresInUse) &&
            task.getLowPerformanceCoresRequired() <=
                (lowPerformanceCores - lowPerformanceCoresInUse) &&
            task.getRamRequiredInMegabytes() <= (totalRamInMegabytes - ramInUseInMegabytes);
  }

  // assigns resources to the task and starts it
  public void handleTask(int serverId, Task task) {
    if (canHandleTask(task)) {
      highPerformanceCoresInUse += task.getHighPerformanceCoresRequired();
      lowPerformanceCoresInUse += task.getLowPerformanceCoresRequired();
      ramInUseInMegabytes += task.getRamRequiredInMegabytes();
      task.startTask();
      task.setAssignedServerId(serverId);
      tasksInProcess.add(task);
      logger.info("Task started: {}", task);
    } else {
      logger.error("Server cannot handle the task due to insufficient resources: {}", task);
      throw new IllegalStateException(
          "Server cannot handle the task due to insufficient resources.");
    }
  }

  // finishes the task and releases the resources
  public void finishTask(Task task) {
    if (tasksInProcess.contains(task)) {
      highPerformanceCoresInUse -= task.getHighPerformanceCoresRequired();
      lowPerformanceCoresInUse -= task.getLowPerformanceCoresRequired();
      ramInUseInMegabytes -= task.getRamRequiredInMegabytes();
      task.setAssignedServerId(-1);
      tasksInProcess.remove(task);
      logger.info("Task finished: {}", task);
    } else {
      logger.error("Task is not being processed by this server: {}", task);
      throw new IllegalStateException("Task is not being processed by this server.");
    }
  }
}