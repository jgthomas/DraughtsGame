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

    public int numberOfCachedMoves() {
        return cachedState.size();
    }

    public void clearCachedMoves() {
        cachedState.clear();
    }

    public void clearCacheFromMove(int moveNumber) {
        if (cachedState.size() > moveNumber) {
            for (int i = moveNumber+1; i < cachedState.size(); i++) {
                cachedState.remove(i);
            }
        }
    }

    public void saveGame(String name) {
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
