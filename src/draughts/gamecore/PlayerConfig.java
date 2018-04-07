package draughts.gamecore;

public final class PlayerConfig {
    private final PlayerType playerType;
    private final PieceType pieceType;

    public PlayerConfig(PlayerType playerType, PieceType pieceType) {
        this.playerType = playerType;
        this.pieceType = pieceType;
    }

    boolean isAiPlayer() {
        return playerType == PlayerType.COMPUTER;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
