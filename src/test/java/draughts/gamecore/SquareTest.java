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
        assertFalse(start.isLegalMoveDistance(end4));
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

    @Test
    public void equalsAndHashCode() {
        Square x = new Square(0,0);
        Square y = new Square(0, 0 );
        assertTrue(x.equals(y) && y.equals(x));
        assertEquals(x.hashCode(), y.hashCode());
    }

    @Test
    public void compareToOverride() {
        Square baseline = new Square(1,1);
        Square same = new Square(1, 1);
        Square lowerRowAndCol = new Square(0, 0);
        Square lowerRow = new Square(0, 1);
        Square higherCol = new Square(1, 2);
        Square higherRow = new Square(2, 0);
        Square higherRowAndCol = new Square(2,2);
        assertEquals(baseline.compareTo(same), 0);
        assertEquals(baseline.compareTo(lowerRowAndCol), 1);
        assertEquals(baseline.compareTo(lowerRow), 1);
        assertEquals(baseline.compareTo(higherCol), -1);
        assertEquals(baseline.compareTo(higherRow), -1);
        assertEquals(baseline.compareTo(higherRowAndCol), -1);
    }
}