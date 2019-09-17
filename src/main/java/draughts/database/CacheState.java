package draughts.database;


import java.util.HashMap;
import java.util.Map;

import draughts.gamecore.Board;
import draughts.gamecore.Square;

public class CacheState {

    private final Board board;
    private final DB db = new DB();
    private final Map<Integer, Map<Integer, Integer>> cachedState;

    public CacheState(Board board) {
        this.board = board;
        cachedState = new HashMap<>();
    }

    public void cacheState(int moveNumber) {
        cachedState.put(moveNumber, captureState());
    }

    public BoardState getCachedState(int moveNumber) {
        return new BoardState(cachedState.get(moveNumber));
    }

    public void setCachedState(Map<Integer, Map<Integer, Integer>> newCachedState) {
        cachedState.clear();
        cachedState.putAll(newCachedState);
    }

    public BoardState getCurrentState() {
        return new BoardState(board);
    }

    public void clearCachedMoves() {
        cachedState.clear();
    }

    public void clearCacheBetween(int startMoveNumber, int endMoveNumber) {
        if (endMoveNumber > startMoveNumber) {
            Map<Integer, Map<Integer, Integer>> newCachedState = new HashMap<>();
            for (Integer key : cachedState.keySet()) {
                if (key <= startMoveNumber) {
                    newCachedState.put(key, cachedState.get(key));
                }
            }
            cachedState.clear();
            cachedState.putAll(newCachedState);
        }
    }

    public void saveCachedState(String name) {
        db.insertGame(name.trim(), cachedState);
    }

    private Map<Integer, Integer> captureState() {
        Map<Integer, Integer> boardState = new HashMap<>();

        for (Square square : board) {
            boardState.put(square.hashCode(), board.getPiece(square).value());
        }

        return boardState;
    }
}
