package draughts.gamecore;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    @Test
    public void hashCodeAndEqualsOverride() {
        Move firstMove = new Move(new Square(0,0), new Square(1,1));
        Move secondMove = new Move(new Square(0, 0), new Square(1, 1));
        Move firstWithNon = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.WHITE_PIECE),
                MoveType.MOVE,
                10);
        Move secondWithNon = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.BLACK_PIECE),
                MoveType.MOVE,
                100);
        assertTrue(firstMove.equals(secondMove) && secondMove.equals(firstMove));
        assertEquals(firstMove.hashCode(), secondMove.hashCode());
        assertTrue(firstWithNon.equals(secondWithNon) && secondWithNon.equals(firstWithNon));
        assertEquals(firstWithNon.hashCode(), secondWithNon.hashCode());
    }

    @Test
    public void compareToOverride() {
        Move first = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.WHITE_PIECE),
                MoveType.MOVE,
                10);
        Move second = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.BLACK_PIECE),
                MoveType.MOVE,
                100);
        Move third = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.BLACK_PIECE),
                MoveType.MOVE,
                1);
        Move fourth = new Move(
                new Square(0,0),
                new Square(1,1),
                new Piece(PieceType.BLACK_PIECE),
                MoveType.MOVE,
                10);
        assertTrue(first.compareTo(second) > 0);
        assertTrue(first.compareTo(third) < 0);
        assertEquals(first.compareTo(fourth), 0);
    }
}