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

    @Test
    public void isLegalMoveDistance() {
        Square start = new Square(0,0);
        Square end1 = new Square(1, 0);
        Square end2 = new Square(0, 1);
        Square end3 = new Square(1, 1);
        Square end4 = new Square(2, 2);
        assertFalse(start.isLegalMoveDistance(end1));
        assertFalse(start.isLegalMoveDistance(end2));
        assertTrue(start.isLegalMoveDistance(end3));
        assertFalse(start.isLegalMoveDistance(end2));
    }

    @Test
    public void isLegalTakeDistance() {
        Square start = new Square(0,0);
        Square end1 = new Square(1, 0);
        Square end2 = new Square(0, 1);
        Square end3 = new Square(1, 1);
        Square end4 = new Square(2, 2);
        Square end5 = new Square(3, 3);
        assertFalse(start.isLegalTakeDistance(end1));
        assertFalse(start.isLegalTakeDistance(end2));
        assertFalse(start.isLegalTakeDistance(end3));
        assertTrue(start.isLegalTakeDistance(end4));
        assertFalse(start.isLegalTakeDistance(end5));
    }
}