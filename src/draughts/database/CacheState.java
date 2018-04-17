package draughts.database;


import java.util.HashMap;
import java.util.Map;

import draughts.gamecore.Board;
import draughts.gamecore.Square;

public class CacheState {

    private final Board board;
    private final DB db = new DB();
    private Map<Integer, Map<Integer, Integer>> cachedState;

    public CacheState(Board board) {
        this.board = board;
        cachedState = new HashMap<>();
    }

    public CacheState(Board board, Map<Integer, Map<Integer, Integer>> cachedState) {
        this.board = board;
        this.cachedState = cachedState;
    }

    public void cacheState(int moveNumber) {
        cachedState.put(moveNumber, captureState());
    }

    public BoardState getCachedState(int moveNumber) {
        return new BoardState(cachedState.get(moveNumber));
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
            System.out.println(cachedState.keySet());
            for (Integer key : cachedState.keySet()) {
                if (key <= startMoveNumber) {
                    newCachedState.put(key, cachedState.get(key));
                }
            }
            cachedState = newCachedState;
            System.out.println(cachedState.keySet());
        }
    }

    public void saveCachedState(String name) {
        db.insertGame(name, cachedState);
    }

    private Map<Integer, Integer> captureState() {
        Map<Integer, Integer> boardState = new HashMap<>();

        for (Square square : board) {
            boardState.put(square.hashCode(), board.getPiece(square).value());
        }

        return boardState;
    }
}
