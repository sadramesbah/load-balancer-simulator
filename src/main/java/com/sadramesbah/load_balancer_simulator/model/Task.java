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
  private int ramRequired;
  private int timeRequired;
  private Instant startTime;

  public Task(int highPerformanceCoresRequired, int lowPerformanceCoresRequired, int ramRequired,
      int timeRequired) {
    this.highPerformanceCoresRequired = highPerformanceCoresRequired;
    this.lowPerformanceCoresRequired = lowPerformanceCoresRequired;
    this.ramRequired = ramRequired;
    this.timeRequired = timeRequired;
    this.startTime = null; // Indicates that the task has not started yet
  }

  // constructor for randomly generating the task
  public Task() {
    Random random = new Random();
    this.highPerformanceCoresRequired = random.nextInt(4) + 1;
    this.lowPerformanceCoresRequired = random.nextInt(2) + 1;
    this.ramRequired = random.nextInt(4) + 1;
    this.timeRequired = random.nextInt(60000) + 1;
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

  public int getRamRequired() {
    return ramRequired;
  }

  public void setRamRequired(int ramRequired) {
    this.ramRequired = ramRequired;
  }

  public int getTimeRequired() {
    return timeRequired;
  }

  public void setTimeRequired(int timeRequired) {
    this.timeRequired = timeRequired;
  }

  public Instant getStartTime() {
    return startTime;
  }

  public void setStartTime(Instant startTime) {
    this.startTime = startTime;
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
