package draughts.ai;

import draughts.gamecore.Board;
import draughts.gamecore.Side;
import draughts.gamecore.Square;

class BoardNav {
    private final Board board;
    private final Side side;

    BoardNav(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    boolean isCentral(Square square) {
        return isCentralCol(square) && isCentralRow(square);
    }

    boolean isCentralCol(Square square) {
        return square.col() > 1 && square.col() < board.sideLength() - 2;
    }

    boolean isCentralRow(Square square) {
        return square.row() > 1 && square.row() < board.sideLength() - 2;
    }

    Square toFrontLeftOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()-1, end.col()-1)
                : new Square(end.row()+1, end.col()+1);
    }

    Square toFrontRightOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()-1, end.col()+1)
                : new Square(end.row()+1, end.col()-1);
    }

    Square toBackLeftOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()+1, end.col()-1)
                : new Square(end.row()-1, end.col()+1);
    }

    Square toBackRightOf(Square end) {
        return (side.isWhite())
                ? new Square(end.row()-1, end.col()+1)
                : new Square(end.row()+1, end.col()+1);
    }

    Square twoBackRightOf(Square square) {
        return toBackRightOf(toBackRightOf(square));
    }

    Square twoBackLeftOf(Square square) {
        return toBackLeftOf(toBackLeftOf(square));
    }
}
