package draughts.gamecore;

import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    @Test
    public void rowHigher() {
        Square start = new Square(0,0);
        Square end1 = new Square(1, 0);
        Square end2 = new Square(0, 1);
        assertTrue(end1.rowHigher(start));
        assertFalse(end2.rowHigher(start));
        assertFalse(start.rowHigher(start));
    }

    @Test
    public void colHigher() {
        Square start = new Square(0,0);
        Square end1 = new Square(1, 0);
        Square end2 = new Square(0, 1);
        assertFalse(end1.colHigher(start));
        assertTrue(end2.colHigher(start));
        assertFalse(start.colHigher(start));
    }

    /*@Test
    public void isLegalMoveDistance() {
    }

    @Test
    public void isLegalTakeDistance() {
    }

    @Test
    public void isWithinRange() {
    }*/
}