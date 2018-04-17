package draughts.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadState {

    private final DB db = new DB();
    private Map<Integer, Map<Integer, Integer>> cachedState;

    public List<String> getAllGameNames() {
        List<String> gameNames =  db.selectAllGameNames();
        if (gameNames.size() < 1) {
            gameNames = Collections.emptyList();
        }
        return gameNames;
    }

    public BoardState loadState(String name) {
        return new BoardState(db.selectGame(name, 0));
    }

    public BoardState loadState(String name, int moveNumber) {
        return new BoardState(db.selectGame(name, moveNumber));
    }

    public int totalMoves(String gameName) {
        return db.totalMovesInGame(gameName);
    }

    private Map<Integer, Map<Integer, Integer>> loadGameToCache(String gameName, int turnNumber) {
        Map<Integer, Map<Integer, Integer>> cachedState = new HashMap<>();
        for (int turn = 0; turn < totalMoves(gameName); turn++) {
            cachedState.put(turn, getTurnState(gameName, turn));
        }
        return cachedState;
    }

    private Map<Integer, Integer> getTurnState(String gameName, int turnNumber) {
        return db.selectGame(gameName, turnNumber);
    }
}
