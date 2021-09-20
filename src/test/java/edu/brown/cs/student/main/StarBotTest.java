package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class StarBotTest {

  @Test
  public void testGetSize() {
    StarBot bot1 = new StarBot("data/stars/ten-star.csv");
    assertEquals(10, bot1.getSize(), 0.01);
    StarBot emptyBot = new StarBot("");
    assertEquals(0, emptyBot.getSize(), 0.01);
  }

  @Test
  public void testSearch() throws Exception {
    StarBot bot1 = new StarBot("data/stars/ten-star.csv");
    assertEquals(0, bot1.searchCoords("Sol").getID(), 0.01);
    assertEquals("Sol", bot1.searchCoords("Sol").getName());
    assertEquals(0, bot1.searchCoords("Sol").getX(), 0.01);
    assertEquals(0, bot1.searchCoords("Sol").getY(), 0.01);
    assertEquals(0, bot1.searchCoords("Sol").getZ(), 0.01);
    assertEquals(0, bot1.searchCoords("Sol").getDist(), 0.01);
    assertEquals("Proxima Centauri",
        bot1.searchCoords("Proxima Centauri").getName());
    assertThrows(NoSuchFieldException.class, () -> {
      bot1.searchCoords("Not a Star");
    });
    StarBot emptyBot = new StarBot("");
    assertThrows(NoSuchFieldException.class, () -> {
      emptyBot.searchCoords("Any Star");
    });
  }

  @Test
  public void testUpdateSortDist() {
    StarBot emptyBot = new StarBot("");
    StarBot bot1 = new StarBot("data/stars/ten-star.csv");
    StarBot bot2 = new StarBot("data/stars/stardata.csv");
    ArrayList<Star> emptyStarList = new ArrayList<Star>();
    assertEquals(emptyStarList, emptyBot.updateSortDist(0, 0, 0));
    bot1.updateSortDist(0, 0, 0);
    for (int i = 0; i < bot1.getSize() - 1; i++) {
      assertTrue(bot1.getStars().get(i).getDist() < bot1.getStars().get(i + 1).getDist());
    }
    bot2.updateSortDist(0, 0, 0);
    for (int i = 0; i < bot2.getSize() - 1; i++) {
      assertTrue(bot2.getStars().get(i).getDist() <= bot2.getStars().get(i + 1).getDist());
    }
  }

}
