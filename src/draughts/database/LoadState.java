package draughts.database;

import draughts.gamecore.PieceType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadState {

    private final DB db = new DB();

    public List<String> getAllGameNames() {
        List<String> gameNames =  db.selectAllGameNames();
        if (gameNames.size() < 1) {
            gameNames = Collections.emptyList();
        }
        return gameNames;
    }

    public Map<Integer, PieceType> loadState(String name, int moveNumber) {
        Map<Integer, Integer> data = db.selectGame(name, moveNumber);
        return convertToBoard(data);
    }

    private Map<Integer, PieceType> convertToBoard(Map<Integer, Integer> data) {
        Map<Integer, PieceType> state = new HashMap<>();
        for (Integer squareKey : data.keySet()) {
            state.put(squareKey, PieceType.valueOf(data.get(squareKey)));
        }
        return state;
    }
}
