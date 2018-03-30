package draughts.gamecore;

public enum MoveType {
    MOVE(1),
    TAKE(100);

    private final int w;

    MoveType(int w) {
        this.w = w;
    }

    public int weight() {
        return this.w;
    }
}
