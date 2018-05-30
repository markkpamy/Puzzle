/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import java.util.Observable;
import Grille.Agent.Color;

/**
 *
 * @author markk
 */
public class Plateau extends Observable {

    private final int nbLignes;
    private final int nbCols;
    private boolean[][] grille;
    private final Case[][] coordCasesGrille;
    private Agent.Color[][] rectPlateau ;
    private Agent currentAgent;
    private final int nBlignesGrille;
    private final int nBcolsGrille;

    public Plateau(int nbLignes, int nbCols, int nBlignesGrille, int nBcolsGrille) {
        this.nbCols = nbCols;
        this.nbLignes = nbLignes;
        this.coordCasesGrille = new Case[nbLignes][nbCols];
        this.grille = new boolean[nbLignes][nbCols];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbCols; j++) {
                this.grille[i][j] = false;
                this.coordCasesGrille[i][j] = new Case(new Position(i, j));
            }
        }
        this.currentAgent = new Agent(nBlignesGrille, nBcolsGrille);
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        rectPlateau = new Agent.Color[nbLignes][nbCols];
    }

    public boolean displayPiece(Agent t) {
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < t.getNbLinesGrid(); i++) {
            for (int j = 0; j < t.getNbColsGrid(); j++) {
               /* if (t.getTABTETRIMINO()[t.getPositionCourante()][compt] == true) {
                    if (((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) < this.nbCols) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) < this.nbLignes) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) >= 0)) {
                        if (this.grille[t.getCoordCaseTetriminos()[i][j].getCoord().getX()][t.getCoordCaseTetriminos()[i][j].getCoord().getY()] == false) {
                            setGrilleCaseTrue(t.getCoordCaseTetriminos()[i][j].getCoord().getX(), t.getCoordCaseTetriminos()[i][j].getCoord().getY());
                        } else {
                            result = false;
                        }
                    } else {
                        result = false;
                    }
                }*/
                compt++;
            }
        }
        return result;
    }

    ;
    public void clearPlateau() {
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbCols; j++) {
                setGrilleCaseFalse(i, j);
            }
        }
    }

    ;
    
    private void setGrilleCaseTrue(int i, int j) {
        if (this.grille[i][j] != true) {
            this.grille[i][j] = true;
            setChanged();
            notifyObservers(getCoordCasesGrille()[i][j]);
        }
    }

    //public void displayPiece(Tetrimino t){
    private void setGrilleCaseFalse(int i, int j) {
        if (this.grille[i][j] != false) {
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
        int compt = 0;
        for (int i = 0; i < t.getNbLinesGrid(); i++) {
            for (int j = 0; j < t.getNbColsGrid(); j++) {
                /*if (t.getTABTETRIMINO()[t.getPositionCourante()][compt] == true) {
                    if (((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) < this.nbCols) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) < this.nbLignes) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) >= 0)) {
                        setGrilleCaseFalse(t.getCoordCaseTetriminos()[i][j].getCoord().getX(), t.getCoordCaseTetriminos()[i][j].getCoord().getY());
                    }
                }*/
                compt++;
            }
        }
    }



    public boolean[][] getGrille() {
        return grille;
    }

    /**
     * @param grille the grille to set
     */
    public void setGrille(boolean[][] grille) {
        this.grille = grille;
    }

    /**
     * @return the coordCasesGrille
     */
    public Case[][] getCoordCasesGrille() {
        return coordCasesGrille;
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
