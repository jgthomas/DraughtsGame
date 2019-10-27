package draughts.gamecore;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LegalMovesTest {
    private final Board board = new Board();
    private final LegalMoves legalMoves = new LegalMoves(board);

    @Test
    public void legal() {
    }

    @Test
    public void legalStartingSquares() {
        List<Square> legalStartsBlack = new ArrayList<>();
        legalStartsBlack.add(new Square(2,1));
        legalStartsBlack.add(new Square(2,1));
        legalStartsBlack.add(new Square(2,3));
        legalStartsBlack.add(new Square(2,3));
        legalStartsBlack.add(new Square(2,5));
        legalStartsBlack.add(new Square(2,5));
        legalStartsBlack.add(new Square(2,7));
        Assert.assertEquals(legalStartsBlack, legalMoves.legalStartingSquares(Side.BLACK));
        List<Square> legalStartsWhite = new ArrayList<>();
        legalStartsWhite.add(new Square(5,0));
        legalStartsWhite.add(new Square(5,2));
        legalStartsWhite.add(new Square(5,2));
        legalStartsWhite.add(new Square(5,4));
        legalStartsWhite.add(new Square(5,4));
        legalStartsWhite.add(new Square(5,6));
        legalStartsWhite.add(new Square(5,6));
        Assert.assertEquals(legalStartsWhite, legalMoves.legalStartingSquares(Side.WHITE));
    }

    @Test
    public void legalEndingSquares() {
    }
}