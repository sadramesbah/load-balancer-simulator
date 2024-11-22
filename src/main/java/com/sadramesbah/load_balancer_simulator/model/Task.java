package com.sadramesbah.load_balancer_simulator.model;

import java.time.Instant;
import java.time.Duration;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task {

  private static final Logger logger = LogManager.getLogger(Task.class);

  private int highPerformanceCoresRequired;
  private int lowPerformanceCoresRequired;
  private int ramRequiredInMegabytes;
  private int timeRequiredInMilliseconds;
  private Instant startTime;
  private int assignedServerId;
  private int assignedHighPerformanceCores;
  private int assignedLowPerformanceCores;

  public Task(int highPerformanceCoresRequired, int lowPerformanceCoresRequired,
      int ramRequiredInMegabytes,
      int timeRequiredInMilliseconds) {
    this.highPerformanceCoresRequired = highPerformanceCoresRequired;
    this.lowPerformanceCoresRequired = lowPerformanceCoresRequired;
    this.ramRequiredInMegabytes = ramRequiredInMegabytes;
    this.timeRequiredInMilliseconds = timeRequiredInMilliseconds;
    this.startTime = null; // indicates the task has not started yet
    this.assignedServerId = -1; // indicates the task has not been assigned to a server yet
    this.assignedHighPerformanceCores = 0;
    this.assignedLowPerformanceCores = 0;
  }

  // constructor for randomly generating the task
  public Task() {
    Random random = new Random();
    // okay to allocate no high-performance cores
    this.highPerformanceCoresRequired = random.nextInt(2);
    // allocates at least 1 low-performance core
    this.lowPerformanceCoresRequired = random.nextInt(2) + 1;
    // allocates 50 to 2000 MB of RAM
    this.ramRequiredInMegabytes = random.nextInt(2000) + 50;
    // assigns 1000 to 15000 milliseconds (15 seconds) of time to complete the task
    this.timeRequiredInMilliseconds = random.nextInt(30000) + 1000;
    // indicates the task has not been assigned to a server yet
    this.assignedServerId = -1;
    this.assignedHighPerformanceCores = 0;
    this.assignedLowPerformanceCores = 0;
  }

  // getters and setters
  public int getHighPerformanceCoresRequired() {
    return highPerformanceCoresRequired;
  }

  public void setHighPerformanceCoresRequired(int highPerformanceCoresRequired) {
    this.highPerformanceCoresRequired = highPerformanceCoresRequired;
  }

  public int getLowPerformanceCoresRequired() {
    return lowPerformanceCoresRequired;
  }

  public void setLowPerformanceCoresRequired(int lowPerformanceCoresRequired) {
    this.lowPerformanceCoresRequired = lowPerformanceCoresRequired;
  }

  public int getRamRequiredInMegabytes() {
    return ramRequiredInMegabytes;
  }

  public void setRamRequiredInMegabytes(int ramRequiredInMegabytes) {
    this.ramRequiredInMegabytes = ramRequiredInMegabytes;
  }

  public int getTimeRequiredInMilliseconds() {
    return timeRequiredInMilliseconds;
  }

  public void setTimeRequiredInMilliseconds(int timeRequiredInMilliseconds) {
    this.timeRequiredInMilliseconds = timeRequiredInMilliseconds;
  }

  public Instant getStartTime() {
    return startTime;
  }

  public void setStartTime(Instant startTime) {
    this.startTime = startTime;
  }

  public int getAssignedServerId() {
    return assignedServerId;
  }

  public void setAssignedServerId(int assignedServerId) {
    this.assignedServerId = assignedServerId;
  }

  public int getAssignedHighPerformanceCores() {
    return assignedHighPerformanceCores;
  }

  public void setAssignedHighPerformanceCores(int assignedHighPerformanceCores) {
    this.assignedHighPerformanceCores = assignedHighPerformanceCores;
  }

  public int getAssignedLowPerformanceCores() {
    return assignedLowPerformanceCores;
  }

  public void setAssignedLowPerformanceCores(int assignedLowPerformanceCores) {
    this.assignedLowPerformanceCores = assignedLowPerformanceCores;
  }

  // sets the start time to the current time
  public void startTask() {
    this.startTime = Instant.now();
  }

  // calculates the elapsed time given a future timestamp
  public Duration getElapsedTime(Instant currentTime) {
    Duration elapsedTime;
    if (startTime == null) {
      logger.info("Task has not started yet! Task: {}", this);
      elapsedTime = Duration.ZERO;
    } else {
      elapsedTime = Duration.between(startTime, currentTime);
      logger.info("Elapsed time: {} milliseconds", elapsedTime.toMillis());
    }
    return elapsedTime;
  }
}
