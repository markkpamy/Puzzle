/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import java.util.List;

import static java.lang.Math.abs;

/**
 *
 * @author markk
 */
public class Agent extends Thread {

    private final int idAgent;
    private final String nameAgent;

    public static enum Color {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    };
    private Color color;
    private Case aCase;

    public Agent(int idAgent, String nameAgent, Case aCase, Color color) {
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.aCase = aCase;
        this.color=color;
    }

    public Agent() {
        this.color = Color.RED;
        idAgent = 0;
        nameAgent ="Agent";
        this.aCase = new Case(new Position(4,4));
    }

    @Override
    public void start() {

    }

    public void moveLeft(Plateau plateau) {
        if (!verifMoveLeft(plateau)) {
            return;
        }
       this.getaCase().getCoord().setY(this.getaCase().getCoord().getY()-1);
    }

    public void moveRight(Plateau plateau) {
        if (!verifMoveRight(plateau)) {
            return;
        }
        this.getaCase().getCoord().setY(this.getaCase().getCoord().getY()+1);
    }

    public void moveDown(Plateau plateau) {
        if (!verifMoveDown(plateau)) {
            return;
        }
        this.getaCase().getCoord().setX(this.getaCase().getCoord().getX()+1);
    }

    public void moveUp(Plateau plateau) {
        if (!verifMoveUp(plateau)) {
            return;
        }
        this.getaCase().getCoord().setX(this.getaCase().getCoord().getX()-1);
    }

    public boolean verifMoveUp(Plateau plateau) {
        if (aCase.getCoord().getX() == 0) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX()][aCase.getCoord().getX() - 1]);
    }
    

    public boolean verifMoveLeft(Plateau plateau) {
        if (plateau.getNbCols() == aCase.getCoord().getY()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getY() - 1][aCase.getCoord().getY()]);
    }

    public boolean verifMoveRight(Plateau plateau) {
        if (plateau.getNbCols() - 1 == aCase.getCoord().getY()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getY() + 1][aCase.getCoord().getY()]);
    }

    public boolean verifMoveDown(Plateau plateau) {
        if (plateau.getNbLignes() - 1 == aCase.getCoord().getX()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX()][aCase.getCoord().getX() + 1]);
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

    public Case getaCase() {
        return aCase;
    }

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }

    public List<Position> getShortestPath() {
//getshortestpath à completer
        return null;
    }

}
