package draughts.ai;

import draughts.gamecore.Board;
import draughts.gamecore.Side;
import draughts.gamecore.Square;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardNavTest {

    @Test
    public void toFrontLeftOfWhite() {
        BoardNav boardNav = new BoardNav(new Board(), Side.WHITE);
        Square start = new Square(5,2);
        Square answer = new Square(4, 1);
        assertEquals(boardNav.toFrontLeftOf(start), answer);
    }

    @Test
    public void toFrontLeftOfBlack() {
        BoardNav boardNav = new BoardNav(new Board(), Side.BLACK);
        Square start = new Square(2, 1);
        Square answer = new Square(3, 2);
        assertEquals(boardNav.toFrontLeftOf(start), answer);
    }

    @Test
    public void toFrontRightOfWhite() {
        BoardNav boardNav = new BoardNav(new Board(), Side.WHITE);
        Square start = new Square(5,2);
        Square answer = new Square(4, 3);
        assertEquals(boardNav.toFrontRightOf(start), answer);
    }

    @Test
    public void toFrontRightOfBlack() {
        BoardNav boardNav = new BoardNav(new Board(), Side.BLACK);
        Square start = new Square(2,1);
        Square answer = new Square(3, 0);
        assertEquals(boardNav.toFrontRightOf(start), answer);
    }

    @Test
    public void toBackLeftOfWhite() {
        BoardNav boardNav = new BoardNav(new Board(), Side.WHITE);
        Square start = new Square(5,2);
        Square answer = new Square(6,1);
        assertEquals(boardNav.toBackLeftOf(start), answer);
    }

    @Test
    public void toBackLeftOfBlack() {
        BoardNav boardNav = new BoardNav(new Board(), Side.BLACK);
        Square start = new Square(2,1);
        Square answer = new Square(1,2);
        assertEquals(boardNav.toBackLeftOf(start), answer);
    }

    @Test
    public void toBackRightOfWhite() {
        BoardNav boardNav = new BoardNav(new Board(), Side.WHITE);
        Square start = new Square(5,2);
        Square answer = new Square(6,3);
        assertEquals(boardNav.toBackRightOf(start), answer);
    }

    @Test
    public void toBackRightOfBlack() {
        BoardNav boardNav = new BoardNav(new Board(), Side.BLACK);
        Square start = new Square(2,1);
        Square answer = new Square(1,0);
        assertEquals(boardNav.toBackRightOf(start), answer);
    }
}