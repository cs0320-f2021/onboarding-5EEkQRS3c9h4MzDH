package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class StarBot {
  private ArrayList<Star> stars;

  /**
   * A constructor for StarBot that takes a filepath and loads Star objects into stars.
   *
   * @param filename - filepath
   */
  public StarBot(String filename) {
    this.stars = new ArrayList<>();
    if (filename.equals("")) {
      this.stars = new ArrayList<>();
    } else {
      try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line = "";
        br.readLine(); // skip first line of headers
        while ((line = br.readLine()) != null) {
          String[] col = line.split(",");
          Star currStar = new Star(Integer.parseInt(col[0]), col[1], Double.parseDouble(col[2]),
              Double.parseDouble(col[3]), Double.parseDouble(col[4]), 0);
          this.stars.add(currStar);
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR: Could not load file");
      }
    }
  }

  protected int getSize() {
    return this.stars.size();
  }

  /**
   * A function that searches for a given starName within this.stars.
   *
   * @param starName - the starName of the given star
   * @return - the Star, if found
   * @throws NoSuchFieldException - if the Star is not found in the list
   */
  public Star searchCoords(String starName) throws NoSuchFieldException {
    Star star = null;
    for (Star curr : this.stars) {
      if (curr.getName().equals(starName)) {
        star = curr;
        System.out.println("TESTING| Found");
      }
    }
    if (star == null) {
      System.out.println("TESTING| Not Found");
      throw new NoSuchFieldException("star not found");
    } else {
      return star;
    }
  }

  /**
   * A function that updates all Star.dist fields to the distance between a given coordinate and the
   * star's coordinate. Then, it sorts them by distance using a custom Comparator.
   *
   * @param x - given x coordinate
   * @param y - given y coordinate
   * @param z - given z coordinate
   * @return - An arraylist of stars, sorted by distance from input coordinate.
   */
  public ArrayList<Star> updateSortDist(double x, double y, double z) {
    for (Star s : this.stars) {
      s.updateDist(x, y, z);
    }
    Comparator<Star> compareByDist = (o1, o2) -> {
      if (o1.getDist() - o2.getDist() > 0) {
        return 1;
      }
      if (o1.getDist() - o2.getDist() < 0) {
        return -1;
      }
      return 0;
    };
    this.stars.sort(compareByDist);
    System.out.println("TESTING| sorted");
    return this.stars;
  }
}
