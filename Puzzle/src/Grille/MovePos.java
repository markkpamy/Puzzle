package Grille;

import Grille.Agent.Move;

public class MovePos {

    private Move move;
    private Position position;

    public MovePos(Move move, Position position) {
        this.move = move;
        this.position = position;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
