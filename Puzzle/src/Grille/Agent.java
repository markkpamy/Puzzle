/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import java.util.List;

import static java.lang.Math.abs;

/**
 * @author markk
 */
public class Agent extends Thread {

    public enum Color {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    }

    public enum Move {
        RIGHT, LEFT, UP, DOWN
    }

    private final int idAgent;
    private final String nameAgent;
    private Color color;
    private Case currentCase;
    private Case goalCase;
    private Plateau plateau;

    public Agent(int idAgent, String nameAgent, Case currentCase, Color color) {
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.currentCase = currentCase;
        this.goalCase = new Case(currentCase.getPosition());
        this.color = color;
    }

    public Agent(int idAgent, String nameAgent, Case currentCase, Case goalCase, Color color) {
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.currentCase = currentCase;
        this.goalCase = goalCase;
        this.color = color;
    }

    public Agent() {
        this.color = Color.RED;
        idAgent = 0;
        nameAgent = "Agent";
        this.currentCase = new Case(new Position(9, 0));
    }

    @Override
    public void start() {
        while (!currentCase.getPosition().equals(goalCase.getPosition())) {
            Move nextMove = chooseNextMove();
            move(this.plateau, nextMove);
        }
    }

    public void moveLeft(Plateau plateau) {
        if (!verifMoveLeft(plateau)) {
            return;
        }
       this.getCurrentCase().getPosition().setY(this.getCurrentCase().getPosition().getY()-1);
    }

    private void sendMessage(Plateau plateau, Position position) {
    }

    public void move(Plateau plateau, Move move) {
        if (verifMove(plateau, move)) {
            this.getCurrentCase().setPosition(positionByMove(move));
        }
    }

    public void moveRight(Plateau plateau) {
        if (!verifMoveRight(plateau)) {
            return;
        }
        this.getCurrentCase().getPosition().setY(this.getCurrentCase().getPosition().getY() + 1);
    }

    public void moveDown(Plateau plateau) {
        if (!verifMoveDown(plateau)) {
            return;
        }
        this.getCurrentCase().getPosition().setX(this.getCurrentCase().getPosition().getX() + 1);
    }

    public void moveUp(Plateau plateau) {
        if (!verifMoveUp(plateau)) {
            return;
        }
        this.getCurrentCase().getPosition().setX(this.getCurrentCase().getPosition().getX() - 1);
    }

    public boolean verifMove(Plateau plateau, Move move) {
        boolean result = true;
        Position position = positionByMove(move);
        switch (move) {
            case RIGHT:
                if ((position.getY() > plateau.getNbCols() - 1)) {
                    return false;
                }
                break;
            case LEFT:
                if (position.getY() < 0) {
                    return  false;
                }
                break;
            case UP:
                if (position.getX() < 0) {
                    return false;
                }
                break;
            case DOWN:
                if (position.getX() > plateau.getNbLignes() - 1) {
                    return false;
                }
                break;
        }
        if (plateau.getGrille()[position.getX()][position.getY()]){
            sendMessage(plateau, position);
            return false;
        }
        return true;
    }

    public boolean verifMoveUp(Plateau plateau) {
        if ((currentCase.getPosition().getX() - 1 >= 0)) {
            return (!plateau.getGrille()[currentCase.getPosition().getX() - 1][currentCase.getPosition().getY()]);
        } else return false;

    }


    public boolean verifMoveLeft(Plateau plateau) {
        if ((currentCase.getPosition().getY() - 1 >= 0)) {
            return !plateau.getGrille()[currentCase.getPosition().getX()][currentCase.getPosition().getY() - 1];
        } else return false;

    }

    public boolean verifMoveRight(Plateau plateau) {
        if ((currentCase.getPosition().getY() + 1 <= plateau.getNbCols() - 1)) {
            return !plateau.getGrille()[currentCase.getPosition().getX()][currentCase.getPosition().getY() + 1];
        } else return false;
    }

    public boolean verifMoveDown(Plateau plateau) {

        if (((currentCase.getPosition().getX() + 1) <= plateau.getNbLignes() - 1)) {
            return !plateau.getGrille()[currentCase.getPosition().getX() + 1][currentCase.getPosition().getY()];
        } else return false;

    }

    /**
     * See which position is the closest to the goal
     */
    public Move chooseNextMove() {
        // right
        Move move = Move.RIGHT;
        Position right = positionByMove(move);
        Position left = positionByMove(Move.LEFT);
        Position up = positionByMove(Move.UP);
        Position down = positionByMove(Move.DOWN);
        double i_best = goalCase.getPosition().getDistance(right);
        double i_left = goalCase.getPosition().getDistance(left);
        if (i_left < i_best) {
            i_best = i_left;
            move = Move.LEFT;
        }
        double i_up = goalCase.getPosition().getDistance(up);
        if (i_up < i_best) {
            i_best = i_up;
            move = Move.UP;
        }
        double i_down = goalCase.getPosition().getDistance(down);
        if (i_down < i_best) {
            move = Move.DOWN;
        }
        return move;
    }


    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public String getNameAgent() {
        return nameAgent;
    }

    public Case getCurrentCase() {
        return currentCase;
    }

    public void setCurrentCase(Case currentCase) {
        this.currentCase = currentCase;
    }

    public List<Position> getShortestPath() {
//getshortestpath à completer
        return null;
    }

    public Case getGoalCase() {
        return goalCase;
    }

    public void setGoalCase(Case goalCase) {
        this.goalCase = goalCase;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Position positionByMove(Move move) {
        Position position = null;
        switch (move) {
            case UP:
                position = new Position(currentCase.getPosition().getX() +1,currentCase.getPosition().getY());
                break;
            case DOWN:
                position = new Position(currentCase.getPosition().getX() -1,currentCase.getPosition().getY());
                break;
            case LEFT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() + 1);
                break;
            case RIGHT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() - 1);
                break;
        }
        return position;
    }
}
