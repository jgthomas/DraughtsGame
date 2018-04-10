package draughts.database;

import draughts.gamecore.Board;
import draughts.gamecore.BoardStateLoader;
import draughts.gamecore.PieceType;
import draughts.gamecore.Square;

import java.util.Map;
import java.util.HashMap;

public class BoardSnapShot implements BoardStateLoader {
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
}
