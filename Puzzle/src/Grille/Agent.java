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
public class Agent implements Cloneable {

    private final int idAgent;
    private final String nameAgent;

    public static enum Color {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    };
    private Color color;
    private Case coordinates;
    private final int nbLinesGrid;
    private final int nbColsGrid;

    public Agent(int idAgent, String nameAgent, Case coordinates, int nbLinesGrid, int nbColsGrid, Color color) {
        this.nbLinesGrid = nbLinesGrid;
        this.nbColsGrid = nbColsGrid;
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.coordinates = coordinates;
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

    public void moveUp() {
        // if (verifMoveRight(plateau) == true) {
        for (int i = 0; i < this.nbLinesGrid; i++) {

        }
    }
    

    public boolean verifMoveLeft(Plateau plateau) {
        //verifier qu'on ne sort pas en dehors du tableau
        boolean result = true;//par défaut on suppose qu'on peut bouger à gauche
       
        return result;
    }

    public boolean verifMoveRight(Plateau plateau) {
        boolean result = true;//par défaut on suppose qu'on peut bouger à droite
       
        return result;
    }

    public boolean verifMoveDown(Plateau plateau) {
        boolean result = true;//par défaut on suppose qu'on peut descendre
        
        return result;
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
     *
     * @return un objet cloné de la classe Tetrimino
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
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

    public Case getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Case coordinates) {
        this.coordinates = coordinates;
    }

}
