package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StarBotTest {

  @Test
  public void testBot() throws Exception {
    StarBot bot1 = new StarBot("data/stars/ten-star.csv");
    assertEquals("Sol", bot1.searchCoords("Sol").getName());
  }
}
