package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void testNegative() {
    MathBot matherator9003 = new MathBot();
    double outputAddNegative = matherator9003.add(-5, -1);
    double outputAddNegative2 = matherator9003.add(-5, 1);
    assertEquals(-6, outputAddNegative, 0.01);
    assertEquals(-4, outputAddNegative2, 0.01);
  }

  @Test
  public void testZeroAdd() {
    MathBot matherator9003 = new MathBot();
    double outputAddZero = matherator9003.add(-5, 0);
    double outputAddZero2 = matherator9003.add(0, 0);
    double outputAddZero3 = matherator9003.add(0, 5);
    assertEquals(-5, outputAddZero, 0.01);
    assertEquals(0, outputAddZero2, 0.01);
    assertEquals(5, outputAddZero3, 0.01);
  }

  @Test
  public void testZeroSubtract() {
    MathBot matherator9003 = new MathBot();
    double outputAddZero = matherator9003.subtract(-5, 0);
    double outputAddZero2 = matherator9003.subtract(0, 0);
    double outputAddZero3 = matherator9003.subtract(0, 5);
    assertEquals(-5, outputAddZero, 0.01);
    assertEquals(0, outputAddZero2, 0.01);
    assertEquals(-5, outputAddZero3, 0.01);
  }

  @Test
  public void testSmallNumbers() {
    MathBot matherator9003 = new MathBot();
    double outputAdd = matherator9003.add(-0.005, 0);
    double outputSubtractZero = matherator9003.subtract(0.0, 0);
    double outputSubtract = matherator9003.subtract(0, 0.005);
    assertEquals(-0.005, outputAdd, 0.00001);
    assertEquals(0, outputSubtractZero, 0.00001);
    assertEquals(-0.005, outputSubtract, 0.00001);
  }
}
