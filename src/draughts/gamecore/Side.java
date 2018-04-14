package draughts.gamecore;

public enum Side {
    WHITE,
    BLACK,
    NONE;

    public boolean isWhite() {
        return this == WHITE;
    }
}
