package draughts.gamecore;

public final class PlayerConfig {
    private final PlayerType playerType;
    private final PieceType pieceType;
    private final Side side;

    public PlayerConfig(PlayerType playerType, PieceType pieceType, Side side) {
        this.playerType = playerType;
        this.pieceType = pieceType;
        this.side = side;
    }

    public boolean isAiPlayer() {
        return playerType == PlayerType.COMPUTER;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }
}
