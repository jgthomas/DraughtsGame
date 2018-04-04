package draughts.gamecore;

public final class PlayerConfig {
    private PlayerType playerType;
    private PieceType pieceType;

    public PlayerConfig(PlayerType playerType, PieceType pieceType) {
        this.playerType = playerType;
        this.pieceType = pieceType;
    }

    public boolean isAiPlayer() {
        return playerType == PlayerType.COMPUTER;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
