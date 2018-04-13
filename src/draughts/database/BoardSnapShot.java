package draughts.database;

import draughts.gamecore.Board;
import draughts.gamecore.BoardStateLoader;
import draughts.gamecore.PieceType;
import draughts.gamecore.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BoardSnapShot implements BoardStateLoader {
    private final int BOARD_SIZE = 8;
    private final Map<Integer, Integer> boardSnapShot;

    BoardSnapShot(Board board) {
        boardSnapShot = new HashMap<>();
        for (Square square : board) {
            boardSnapShot.put(square.hashCode(), board.getPieceType(square).value());
        }
    }

    BoardSnapShot(Map<Integer, Integer> boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public PieceType getPieceType(Square square) {
        return PieceType.valueOf(boardSnapShot.get(square.hashCode()));
    }

    public List<Square> squares() {
        List<Square> squares = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares.add(new Square(row, col));
            }
        }
        return squares;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}
