package draughts.ai;

import draughts.gamecore.Move;

import java.util.List;

public interface MoveRater {

    List<Move> ratedMoves(List<Move> legalMoves);

}
