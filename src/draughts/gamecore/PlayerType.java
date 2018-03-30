package draughts.gamecore;


import java.util.HashMap;
import java.util.Map;

public enum PlayerType {
    HUMAN(1),
    COMPUTER(2);

    private static final Map<Integer, PlayerType> playerTypeCodes = new HashMap<>();

    static {
        for (PlayerType playerType : PlayerType.values()) {
            playerTypeCodes.put(playerType.pickNum, playerType);
        }
    }

    private final int pickNum;

    PlayerType(int pickNum) {
        this.pickNum = pickNum;
    }

    public static PlayerType valueOf(int pickNum) {
        return playerTypeCodes.get(pickNum);
    }

    public int pick() {
        return pickNum;
    }
}
