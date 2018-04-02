package draughts.database;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import draughts.gamecore.Board;
import draughts.gamecore.Square;

public class SaveState {

    private final Board board;
    private final DB db;
    private final List<State> allMoves = new ArrayList<>();
    private final Map<Integer, Map<Integer, Integer>> cachedState = new HashMap<>();

    public SaveState(Board board) {
        this.board = board;
        db = new DB();
    }

    public void saveGame(String name) {
        db.insertGame(name, allMoves);
    }

    public void cacheState(int moveNumber) {
        State state = new State(moveNumber, captureState());
        allMoves.add(state);
    }

    public void saveCachedState(int moveNumber) {
        cachedState.put(moveNumber, captureState());
    }

    public Map<Integer, Integer> getCachedState(int moveNumber) {
        return cachedState.get(moveNumber);
    }

    private Map<Integer, Integer> captureState() {
        Map<Integer, Integer> boardState = new HashMap<>();

        for (Square square : board) {
            boardState.put(square.hashCode(), board.getPieceType(square).value());
        }

        return boardState;
    }

    final class State {
        final int moveNumber;
        final Map<Integer, Integer> state;

        State(int moveNumber, Map<Integer, Integer> state) {
            this.moveNumber = moveNumber;
            this.state = state;
        }
    }
}
