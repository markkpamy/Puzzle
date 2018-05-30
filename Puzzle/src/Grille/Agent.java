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
    private final String nomAgent;

    public static enum Couleur {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    };
    private Couleur color;
    private Case coordonnes;
    private final int nBlignesGrille;
    private final int nBcolsGrille;

    public Agent(int idAgent, String nomAgent, Case coordonnes, int nBlignesGrille, int nBcolsGrille, Couleur color) {
        this.nBlignesGrille = nBlignesGrille;
        this.nBcolsGrille = nBcolsGrille;
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.coordonnes = coordonnes;
        this.color=color;
    }

    public Agent(int nBlignesGrille, int nBcolsGrille) {
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        this.color = Couleur.BLACK;
        idAgent = 0;
        nomAgent ="Agent";
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

    public void moveUp(Plateau plateau) {
        // if (verifMoveRight(plateau) == true) {
        for (int i = 0; i < this.nBlignesGrille; i++) {

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
     * @return the nBlignesGrille
     */
    public int getnBlignesGrille() {
        return nBlignesGrille;
    }

    /**
     * @param nBlignesGrille the nBlignesGrille to set
     */
    /**
     * @return the nBcolsGrille
     */
    public int getnBcolsGrille() {
        return nBcolsGrille;
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
    public Couleur getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Couleur color) {
        this.color = color;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public Case getCoordonnes() {
        return coordonnes;
    }

    public void setCoordonnes(Case coordonnes) {
        this.coordonnes = coordonnes;
    }

}
