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
        whitePositions = new ArrayList<>(Arrays.asList(0,2,4,6,9,11,13,15,16,18,20,22));
        blackPositions = new ArrayList<>(Arrays.asList(41,43,45,47,48,50,52,54,57,59,61,63));
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
