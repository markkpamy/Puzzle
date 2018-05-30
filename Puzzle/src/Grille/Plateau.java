/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModuleGestionPlateau;

import ModuleGestionPlateau.Tetrimino.Couleur;
import java.util.Observable;

/**
 *
 * @author markk
 */
public class Plateau extends Observable {

    private final int nbLignes;
    private final int nbCols;
    private int lines, level;
    private boolean[][] grille;
    private final Case[][] coordCasesGrille;
    private Couleur [][] rectPlateau ;
    private Tetrimino currentTetrimino;
    private final int nBlignesGrille;
    private final int nBcolsGrille;
    private int points;

    public Plateau(int nbLignes, int nbCols, int nBlignesGrille, int nBcolsGrille) {
        this.nbCols = nbCols;
        this.level = 1;
        this.points = 0;
        this.nbLignes = nbLignes;
        this.coordCasesGrille = new Case[nbLignes][nbCols];
        this.grille = new boolean[nbLignes][nbCols];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbCols; j++) {
                this.grille[i][j] = false;
                this.coordCasesGrille[i][j] = new Case(new Coordonnees(i, j));
            }
        }
        this.currentTetrimino = new Tetrimino(nBlignesGrille, nBcolsGrille);
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        rectPlateau = new Couleur[nbLignes][nbCols];
    }

    public boolean displayPiece(Tetrimino t) {
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < t.getnBlignesGrille(); i++) {
            for (int j = 0; j < t.getnBcolsGrille(); j++) {
                if (t.getTABTETRIMINO()[t.getPositionCourante()][compt] == true) {
                    if (((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) < this.nbCols) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) < this.nbLignes) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) >= 0)) {
                        if (this.grille[t.getCoordCaseTetriminos()[i][j].getCoord().getX()][t.getCoordCaseTetriminos()[i][j].getCoord().getY()] == false) {
                            setGrilleCaseTrue(t.getCoordCaseTetriminos()[i][j].getCoord().getX(), t.getCoordCaseTetriminos()[i][j].getCoord().getY());
                        } else {
                            result = false;
                        }
                    } else {
                        result = false;
                    }
                }
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
    public void effaceTracePiece(Tetrimino t) {
        int compt = 0;
        for (int i = 0; i < t.getnBlignesGrille(); i++) {
            for (int j = 0; j < t.getnBcolsGrille(); j++) {
                if (t.getTABTETRIMINO()[t.getPositionCourante()][compt] == true) {
                    if (((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getY()) < this.nbCols) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) < this.nbLignes) && ((t.getCoordCaseTetriminos()[i][j].getCoord().getX()) >= 0)) {
                        setGrilleCaseFalse(t.getCoordCaseTetriminos()[i][j].getCoord().getX(), t.getCoordCaseTetriminos()[i][j].getCoord().getY());
                    }
                }
                compt++;
            }
        }
    }

    public void removeFullLines(Plateau plateau) {
        boolean result;
        int linestmp = 0;
        int tmp1[] = {0, 0};
        Integer[] tmp2 = new Integer[2];
        Integer[] tmp3 = new Integer[2];
        for (int m = 0; m < plateau.nbLignes; m++) {
            result = true;
            for (int n = 0; n < plateau.nbCols; n++) {
                if (plateau.getGrille()[m][n] == false) {
                    result = false;
                }
            }
            if (result == true) {
                linestmp++;
                for (int i = 0; i < plateau.nbCols; i++) {
                    plateau.setGrilleCaseFalse(m, i);
                }
                for (int t = m; t > 0; t--) {
                    for (int o = 0; o < plateau.nbCols; o++) {
                        if (plateau.getGrille()[t][o] != plateau.getGrille()[t - 1][o]) {
                            plateau.getGrille()[t][o] = plateau.getGrille()[t - 1][o];
                            setChanged();
                            notifyObservers(getCoordCasesGrille()[t][o]);
                        }
                    }
                }
            }
        }
        if (linestmp > 0) {
            //calcul de points
            lines += linestmp;
            points = points + (50 * getLevel());
            tmp1[0] = 0;
            tmp1[1] = points;
            tmp2[0] = tmp1[0];
            tmp2[1] = tmp1[1];
            tmp3[0] = 1;
            tmp3[1] = lines;
            setChanged();
            notifyObservers(tmp2);
            setChanged();
            notifyObservers(tmp3);
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
    public Tetrimino getCurrentTetrimino() {
        return currentTetrimino;
    }

    /**
     * @param currentTetrimino the currentTetrimino to set
     */
    public void setCurrentTetrimino(Tetrimino currentTetrimino) {
        this.currentTetrimino = currentTetrimino;
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
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the lines
     */
    public int getLines() {
        return lines;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the rectPlateau
     */
    public Couleur[][] getRectPlateau() {
        return rectPlateau;
    }

    /**
     * @param rectPlateau the rectPlateau to set
     */
    public void setRectPlateau(Couleur[][] rectPlateau) {
        this.rectPlateau = rectPlateau;
    }
}
