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

  public Task(int highPerformanceCoresRequired, int lowPerformanceCoresRequired,
      int ramRequiredInMegabytes,
      int timeRequiredInMilliseconds) {
    this.highPerformanceCoresRequired = highPerformanceCoresRequired;
    this.lowPerformanceCoresRequired = lowPerformanceCoresRequired;
    this.ramRequiredInMegabytes = ramRequiredInMegabytes;
    this.timeRequiredInMilliseconds = timeRequiredInMilliseconds;
    this.startTime = null; // indicates the task has not started yet
    this.assignedServerId = -1; // indicates the task has not been assigned to a server yet
  }

  // constructor for randomly generating the task
  public Task() {
    Random random = new Random();
    // okay to allocate no high-performance cores
    this.highPerformanceCoresRequired = random.nextInt(2);
    // allocates at least 1 low-performance core
    this.lowPerformanceCoresRequired = random.nextInt(2) + 1;
    // allocates 1 to 1500 MB of RAM
    this.ramRequiredInMegabytes = random.nextInt(1500) + 1;
    // assigns 1 to 60000 milliseconds (1 minute) of time
    this.timeRequiredInMilliseconds = random.nextInt(60000) + 1;
    // indicates the task has not been assigned to a server yet
    this.assignedServerId = -1;
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

  // sets the start time to the current time
  public void startTask() {
    this.startTime = Instant.now();
  }

  // calculates the elapsed time given a future timestamp
  public Duration getElapsedTime(Instant futureTime) {
    if (startTime == null) {
      throw new IllegalStateException("Task has not been started yet!");
    }
    Duration elapsedTime = Duration.between(startTime, futureTime);
    logger.info("Elapsed time: {} milliseconds", elapsedTime.toMillis());
    return elapsedTime;
  }
}
