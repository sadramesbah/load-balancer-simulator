package com.sadramesbah.load_balancer_simulator.model;

public class Server {

  private int highPerformanceCores;
  private int lowPerformanceCores;
  private int totalRam;
  private int highPerformanceCoresInUse;
  private int lowPerformanceCoresInUse;
  private int ramInUse;

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
}