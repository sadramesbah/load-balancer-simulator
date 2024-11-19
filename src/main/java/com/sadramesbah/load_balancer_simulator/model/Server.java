package com.sadramesbah.load_balancer_simulator.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

  private static final Logger logger = LogManager.getLogger(Server.class);

  private int highPerformanceCores;
  private int lowPerformanceCores;
  private int totalRam;
  private int highPerformanceCoresInUse;
  private int lowPerformanceCoresInUse;
  private int ramInUse;
  private List<Task> tasksInProcess = new ArrayList<>();

  public Server() {
  }

  public Server(int highPerformanceCores, int lowPerformanceCores, int totalRam) {
    this.highPerformanceCores = highPerformanceCores;
    this.lowPerformanceCores = lowPerformanceCores;
    this.totalRam = totalRam;
    this.highPerformanceCoresInUse = 0;
    this.lowPerformanceCoresInUse = 0;
    this.ramInUse = 0;
  }

  // Getters and Setters
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
    return totalRam;
  }

  public void setTotalRam(int totalRam) {
    this.totalRam = totalRam;
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
    return ramInUse;
  }

  public void setRamInUse(int ramInUse) {
    this.ramInUse = ramInUse;
  }

  // checks if current instance of server can handle the task
  private boolean canHandleTask(Task task) {
    return
        task.getHighPerformanceCoresRequired() <=
            (highPerformanceCores - highPerformanceCoresInUse) &&
            task.getLowPerformanceCoresRequired() <=
                (lowPerformanceCores - lowPerformanceCoresInUse) &&
            task.getRamRequired() <= (totalRam - ramInUse);
  }

  // assigns resources to the task and starts it
  public void handleTask(Task task) {
    if (canHandleTask(task)) {
      highPerformanceCoresInUse += task.getHighPerformanceCoresRequired();
      lowPerformanceCoresInUse += task.getLowPerformanceCoresRequired();
      ramInUse += task.getRamRequired();
      task.startTask();
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
      ramInUse -= task.getRamRequired();
      tasksInProcess.remove(task);
      logger.info("Task finished: {}", task);
    } else {
      logger.error("Task is not being processed by this server: {}", task);
      throw new IllegalStateException("Task is not being processed by this server.");
    }
  }
}