package draughts.gamecore;

public final class PlayerConfig {
    private final PlayerType playerType;
    private final Side side;

    public PlayerConfig(PlayerType playerType, Side side) {
        this.playerType = playerType;
        this.side = side;
    }

    public boolean isAiPlayer() {
        return playerType == PlayerType.COMPUTER;
    }

    public Side getSide() {
        return side;
    }
}
