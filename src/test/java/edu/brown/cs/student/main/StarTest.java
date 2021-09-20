package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StarTest {

  @Test
  public void testUpdateDist() {
    Star zeroStar = new Star(0, "Zero", 0, 0, 0, 0);
    zeroStar.updateDist(0, 0, 0);
    assertEquals(0, zeroStar.getDist(), 0.01);
    zeroStar.updateDist(2, 0, 0);
    assertEquals(2, zeroStar.getDist(), 0.01);
    zeroStar.updateDist(-2, 0, 0);
    assertEquals(2, zeroStar.getDist(), 0.01);
    zeroStar.updateDist(2, -2, 2);
    assertEquals(3.4641, zeroStar.getDist(), 0.01);
  }

}
