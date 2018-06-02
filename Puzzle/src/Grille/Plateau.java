/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import java.util.Observable;

import Grille.Agent.Color;

/**
 * @author markk
 */
public class Plateau extends Observable {

    private final int nbLignes;
    private final int nbCols;
    private boolean[][] grille;
    private final Case[][] panCases;
    private Agent.Color[][] rectPlateau;
    private Agent currentAgent;
    private final int nBlignesGrille;
    private final int nBcolsGrille;

    public Plateau(int nbLignes, int nbCols, int nBlignesGrille, int nBcolsGrille) {
        this.nbCols = nbCols;
        this.nbLignes = nbLignes;
        this.panCases = new Case[nbLignes][nbCols];
        this.grille = new boolean[nbLignes][nbCols];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbCols; j++) {
                this.grille[i][j] = false;
                this.panCases[i][j] = new Case(new Position(i, j));
            }
        }
        this.currentAgent = new Agent(nBlignesGrille, nBcolsGrille);
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        rectPlateau = new Agent.Color[nbLignes][nbCols];
    }

    public boolean displayPiece(Agent t) {
        boolean result = true;
        if (!this.grille[t.getaCase().getCoord().getX()][t.getaCase().getCoord().getY()]) {
            setGrilleCaseTrue(t.getaCase().getCoord().getX(), t.getaCase().getCoord().getY());
        } else {
            result = false;
        }
        return result;
    }


    public void clearPlateau() {
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbCols; j++) {
                setGrilleCaseFalse(i, j);
            }
        }
    }

    ;

    public void setGrilleCaseTrue(int i, int j) {
        if (!this.grille[i][j]) {
            this.grille[i][j] = true;
            setChanged();
            notifyObservers(getCoordCasesGrille()[i][j]);
        }
    }

    //public void displayPiece(Tetrimino t){
    public void setGrilleCaseFalse(int i, int j) {
        if (this.grille[i][j]) {
            this.grille[i][j] = false;
            setChanged();
            notifyObservers(getCoordCasesGrille()[i][j]);
        }
    }

    //}

    /**
     * @param t
     */
    public void effaceTracePiece(Agent t) {
        if (!this.grille[t.getaCase().getCoord().getX()][t.getaCase().getCoord().getY()]) {
            setGrilleCaseFalse(t.getaCase().getCoord().getX(), t.getaCase().getCoord().getY());
        }
    }


    public boolean[][] getGrille() {
        return grille;
    }

    /*K*
     * @param grille the grille to set
     */
    public void setGrille(boolean[][] grille) {
        this.grille = grille;
    }

    /**
     * @return the coordCasesGrille
     */
    public Case[][] getCoordCasesGrille() {
        return panCases;
    }

    /**
     * @return the currentTetrimino
     */
    public Agent getCurrentAgent() {
        return currentAgent;
    }

    /**
     * @param currentAgent the currentTetrimino to set
     */
    public void setCurrentAgent(Agent currentAgent) {
        this.currentAgent = currentAgent;
    }

    /**
     * @return the nbLignes
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * @return the nbCols
     */
    public int getNbCols() {
        return nbCols;
    }

    /**
     * @return the nBlignesGrille
     */
    public int getnBlignesGrille() {
        return nBlignesGrille;
    }

    /**
     * @return the nBcolsGrille
     */
    public int getnBcolsGrille() {
        return nBcolsGrille;
    }


    /**
     * @return the rectPlateau
     */
    public Color[][] getRectPlateau() {
        return rectPlateau;
    }

    /**
     * @param rectPlateau the rectPlateau to set
     */
    public void setRectPlateau(Color[][] rectPlateau) {
        this.rectPlateau = rectPlateau;
    }
}
