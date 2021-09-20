package edu.brown.cs.student.main;

public class Star {
  private int starID;
  private String name;
  private double x;
  private double y;
  private double z;
  private double dist;

  /**
   * A constructor for Star.
   *
   * @param starID - starID
   * @param name - star name
   * @param x - x coordinate as given
   * @param y - y coordinate as given
   * @param z - z coordinate as given
   * @param dist - by default, zero, but is later calculated in updateDist
   */
  public Star(int starID, String name, double x, double y, double z, double dist) {
    this.starID = starID;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.dist = dist;
  }

  protected int getID() {
    return this.starID;
  }

  protected String getName() {
    return this.name;
  }

  protected double getX() {
    return this.x;
  }

  protected double getY() {
    return this.y;
  }

  protected double getZ() {
    return this.z;
  }

  protected double getDist() {
    return this.dist;
  }

  /**
   * A function that calculates and updates the distance between a Star and another coordinate.
   *
   * @param xi - x coordinate of other object
   * @param yi - y coordinate of other object
   * @param zi - z coordinate of other object
   */
  protected void updateDist(double xi, double yi, double zi) {
    this.dist = Math.sqrt(Math.pow(xi - this.x, 2)
        + Math.pow(yi - this.y, 2) + Math.pow(zi - this.z, 2));
  }

}
