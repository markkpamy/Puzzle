package model;

import Grille.Plateau;
import Grille.Position;
import Grille.ShorthestPast;

public class Agent extends Thread {

    private Position currentPosition;
    private Position goalPosition;


    @Override
    public void start() {

    }

    public void moveLeft(Plateau plateau) {
        if (!verifMoveLeft(plateau)) {
            return;
        }
        plateau.setGrilleCaseFalse(currentPosition.getX(), currentPosition.getY());
        plateau.setGrilleCaseTrue(currentPosition.getX() - 1, currentPosition.getY());
    }

    public void moveRight(Plateau plateau) {
        if (!verifMoveRight(plateau)) {
            return;
        }
        plateau.setGrilleCaseFalse(currentPosition.getX(), currentPosition.getY());
        plateau.setGrilleCaseTrue(currentPosition.getX() + 1, currentPosition.getY());
    }

    public void moveDown(Plateau plateau) {
        if (!verifMoveDown(plateau)) {
            return;
        }

        plateau.setGrilleCaseFalse(currentPosition.getX(), currentPosition.getY());
        plateau.setGrilleCaseTrue(currentPosition.getX(), currentPosition.getY() - 1);
    }

    public void moveUp(Plateau plateau) {
        if (!verifMoveDown(plateau)) {
            return;
        }

        plateau.setGrilleCaseFalse(currentPosition.getX(), currentPosition.getY());
        plateau.setGrilleCaseTrue(currentPosition.getX(), currentPosition.getY() + 1);
    }

    public boolean verifMoveUp(Plateau plateau) {
        if (currentPosition.getX() == 0) {
            return false;
        }
        return !(plateau.getGrille()[currentPosition.getX()][currentPosition.getX() - 1]);
    }


    public boolean verifMoveLeft(Plateau plateau) {
        if (plateau.getNbCols() == currentPosition.getX()) {
            return false;
        }
        return !(plateau.getGrille()[currentPosition.getX() - 1][currentPosition.getX()]);
    }

    public boolean verifMoveRight(Plateau plateau) {
        if (plateau.getNbCols() - 1 == currentPosition.getX()) {
            return false;
        }
        return !(plateau.getGrille()[currentPosition.getX() + 1][currentPosition.getX()]);
    }

    public boolean verifMoveDown(Plateau plateau) {
        if (plateau.getNbLignes() - 1 == currentPosition.getY()) {
            return false;
        }
        return !(plateau.getGrille()[currentPosition.getX()][currentPosition.getX() + 1]);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ShorthestPast getShortestPath() {
        //@TODO
        ShorthestPast shorthestPast = null;
        return shorthestPast;
    }
}
