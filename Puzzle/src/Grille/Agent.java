/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

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
    private final int nbLinesGrid;
    private final int nbColsGrid;

    public Agent(int idAgent, String nameAgent, Case aCase, int nbLinesGrid, int nbColsGrid, Color color) {
        this.nbLinesGrid = nbLinesGrid;
        this.nbColsGrid = nbColsGrid;
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.aCase = aCase;
        this.color=color;
    }

    public Agent(int nbLinesGrid, int nbColsGrid) {
        this.nbColsGrid = nbColsGrid;
        this.nbLinesGrid = nbLinesGrid;
        this.color = Color.BLACK;
        idAgent = 0;
        nameAgent ="Agent";
    }

    public void moveLeft(Plateau plateau) {
        if (verifMoveLeft(plateau) == true) {

        }
    }

    public void moveRight(Plateau plateau) {
        if (verifMoveRight(plateau)) {

        }
    }

    public void moveDown(Plateau plateau) {
        if (verifMoveDown(plateau)) {

        }
    }

    public boolean moveUp(Plateau plateau) {
        if (plateau.getNbLignes() == aCase.getCoord().getY()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX()][aCase.getCoord().getX() - 1]);
    }
    

    public boolean verifMoveLeft(Plateau plateau) {
        if (plateau.getnBcolsGrille() == aCase.getCoord().getX()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX() - 1][aCase.getCoord().getX()]);
    }

    public boolean verifMoveRight(Plateau plateau) {
        if (plateau.getnBcolsGrille() - 1 == aCase.getCoord().getX()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX() + 1][aCase.getCoord().getX()]);
    }

    public boolean verifMoveDown(Plateau plateau) {
        if (plateau.getNbLignes() - 1 == aCase.getCoord().getY()) {
            return false;
        }
        return !(plateau.getGrille()[aCase.getCoord().getX()][aCase.getCoord().getX() + 1]);
    }

    /**
     * @return the nbLinesGrid
     */
    public int getNbLinesGrid() {
        return nbLinesGrid;
    }

    /**
     * @param nbLinesGrid the nbLinesGrid to set
     */
    /**
     * @return the nbColsGrid
     */
    public int getNbColsGrid() {
        return nbColsGrid;
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

}
