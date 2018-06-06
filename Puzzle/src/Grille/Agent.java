/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import Comm.Communication;
import Comm.Message;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;

/**
 * @author markk
 */
public class Agent implements Runnable {

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
        this(idAgent, nameAgent, currentCase, new Case(new Position(currentCase.getPosition().getY(), currentCase.getPosition().getX())), color);
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
    public void run() {
       // System.out.println(currentCase.getPosition());
       // System.out.println(goalCase.getPosition());
        while (!currentCase.getPosition().equals(goalCase.getPosition())) {
            //System.out.println("dans le while");
            Move nextMove = chooseNextMove();
            this.plateau.effaceTracePiece(this);
            move(this.plateau, nextMove);
            this.plateau.updatePlateau(this);
            receiveAndMove();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveAndMove() {
        Message message = null;
        if ((message = Communication.getInstance().readMessage(this)) != null) {
            switch (message.getType()) {
                case "request":

                    switch (message.getAction()) {
                        case "move":
                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    break;
            }

        }
    }

    private void sendMessage(Plateau plateau, Position position) {
        Agent agent = plateau.findAgent(position);
        Message message = new Message(this,agent,"request","move",goalCase.getPosition());
        Communication.getInstance().writeMessage(agent,message);
    }

    public synchronized void move(Plateau plateau, Move move) {
        if (verifMove(plateau, move)) {
            //System.out.println(this);
            this.getCurrentCase().setPosition(positionByMove(move));
        }
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
       // System.out.println(position);
        if (plateau.getGrille()[position.getX()][position.getY()]){
            sendMessage(plateau, position);
            return false;
        }
        return true;
    }
    /**
     * See which position is the closest to the goal
     */
    public synchronized Move chooseNextMove() {
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
                position = new Position(currentCase.getPosition().getX() -1,currentCase.getPosition().getY());
                break;
            case DOWN:
                position = new Position(currentCase.getPosition().getX() +1,currentCase.getPosition().getY());
                break;
            case LEFT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() - 1);
                break;
            case RIGHT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() + 1);
                break;
        }
        return position;
    }

    @Override
    public String toString() {
        return "Agent:"+this.color + " position:" + this.currentCase.getPosition() + " destination:" + this.goalCase.getPosition();
    }
}
