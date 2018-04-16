package draughts.database;

import draughts.gamecore.Board;
import draughts.gamecore.BoardStateLoader;
import draughts.gamecore.Square;

import java.util.Map;
import java.util.HashMap;

class BoardState implements BoardStateLoader {
    private final Map<Integer, Integer> boardSnapShot;

    BoardState(Board board) {
        boardSnapShot = new HashMap<>();
        for (Square square : board) {
            boardSnapShot.put(square.hashCode(), board.getPiece(square).value());
        }
    }

    BoardState(Map<Integer, Integer> boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public int getPieceCode(Square square) {
        return boardSnapShot.get(square.hashCode());
    }
}
