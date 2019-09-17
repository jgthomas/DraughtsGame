package draughts.gamecore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Side {
    WHITE,
    BLACK,
    NONE;

    private static final List<Integer> whitePositions;
    private static final List<Integer> blackPositions;

    static {
        whitePositions = new ArrayList<>(Arrays.asList(40,42,44,46,49,51,53,55,56,58,60,62));
        blackPositions = new ArrayList<>(Arrays.asList(1,3,5,7,8,10,12,14,17,19,21,23));
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    static List<Integer> getWhite() {
        return whitePositions;
    }

    static List<Integer> getBlack() {
        return blackPositions;
    }
}
