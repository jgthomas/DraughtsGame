package draughts.database;

import java.util.Collections;
import java.util.List;

public class LoadState {

    private final DB db = new DB();

    public List<String> getAllGameNames() {
        List<String> gameNames =  db.selectAllGameNames();
        if (gameNames.size() < 1) {
            gameNames = Collections.emptyList();
        }
        return gameNames;
    }

    public BoardSnapShot loadState(String name) {
        return new BoardSnapShot(db.selectGame(name, 0));
    }

    public BoardSnapShot loadState(String name, int moveNumber) {
        return new BoardSnapShot(db.selectGame(name, moveNumber));
    }

    public int totalMoves(String gameName) {
        return db.totalMovesInGame(gameName);
    }
}
