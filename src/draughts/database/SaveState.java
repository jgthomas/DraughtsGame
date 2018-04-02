package draughts.database;


import java.util.HashMap;
import java.util.Map;

import draughts.gamecore.Board;
import draughts.gamecore.Square;

public class SaveState {

    private final Board board;
    private final DB db;
    private final Map<Integer, Map<Integer, Integer>> cachedState = new HashMap<>();

    public SaveState(Board board) {
        this.board = board;
        db = new DB();
    }

    public void cacheState(int moveNumber) {
        cachedState.put(moveNumber, captureState());
    }

    public Map<Integer, Integer> getCachedState(int moveNumber) {
        return cachedState.get(moveNumber);
    }

    public void saveGame(String name) {
        db.insertGame(name, cachedState);
    }

    private Map<Integer, Integer> captureState() {
        Map<Integer, Integer> boardState = new HashMap<>();

        for (Square square : board) {
            boardState.put(square.hashCode(), board.getPieceType(square).value());
        }

        return boardState;
    }
}
