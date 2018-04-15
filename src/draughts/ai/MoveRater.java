package draughts.ai;

import draughts.gamecore.Move;

import java.util.List;

interface MoveRater {

    List<Move> rateMoves(List<Move> legalMoves);

}
