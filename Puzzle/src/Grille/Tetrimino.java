/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModuleGestionPlateau;

import static java.lang.Math.abs;

/**
 *
 * @author markk
 */
public class Tetrimino implements Cloneable {

    private final int idTetrimino;
    private final String nomTetrimino;
    private int positionCourante;
    private boolean[][] TABTETRIMINO;

    public static enum Couleur {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    };
    private Couleur color;
    private Case[][] coordCaseTetriminos;
    private final int nBlignesGrille;
    private final int nBcolsGrille;

    public Tetrimino(int idTetrimino, String nomTetrimino, int positionCourante, boolean[][] TABTETRIMINO, int nBlignesGrille, int nBcolsGrille, Couleur color) {
        this.coordCaseTetriminos = new Case[nBlignesGrille][nBcolsGrille];
        this.idTetrimino = idTetrimino;
        this.nomTetrimino = nomTetrimino;
        this.positionCourante = positionCourante;
        this.TABTETRIMINO = TABTETRIMINO;
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        this.color = color;
        initCoord();
    }

    public Tetrimino(int nBlignesGrille, int nBcolsGrille) {
        this.coordCaseTetriminos = new Case[nBlignesGrille][nBcolsGrille];
        this.idTetrimino = 0;
        this.nomTetrimino = "Tetrimino";
        this.positionCourante = 0;
        //initialiser à false
        this.TABTETRIMINO = null;
        this.nBcolsGrille = nBcolsGrille;
        this.nBlignesGrille = nBlignesGrille;
        this.color = Couleur.BLACK;
        initCoord();
    }

    public void moveLeft(Plateau plateau) {
        if (verifMoveLeft(plateau) == true) {
            for (int i = 0; i < this.nBlignesGrille; i++) {
                for (int j = 0; j < this.nBcolsGrille; j++) {
                    this.getCoordCaseTetriminos()[i][j].getCoord().setY(this.getCoordCaseTetriminos()[i][j].getCoord().getY() - 1);
                }
            }
        }
    }

    public void moveRight(Plateau plateau) {
        if (verifMoveRight(plateau) == true) {
            for (int i = 0; i < this.nBlignesGrille; i++) {
                for (int j = 0; j < this.nBcolsGrille; j++) {
                    this.getCoordCaseTetriminos()[i][j].getCoord().setY(this.getCoordCaseTetriminos()[i][j].getCoord().getY() + 1);
                }
            }
        }
    }

    public void moveDown(Plateau plateau) {
        if (verifMoveDown(plateau) == true) {
            for (int i = 0; i < this.nBlignesGrille; i++) {
                for (int j = 0; j < this.nBcolsGrille; j++) {
                    this.getCoordCaseTetriminos()[i][j].getCoord().setX(this.getCoordCaseTetriminos()[i][j].getCoord().getX() + 1);
                }
            }

        }

    }

    private void moveUp() {
        // if (verifMoveRight(plateau) == true) {
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                this.getCoordCaseTetriminos()[i][j].getCoord().setX(this.getCoordCaseTetriminos()[i][j].getCoord().getX() - 1);
            }
        }
        // }
    }

    public void rotate(Plateau plateau) {
        int oldPosition = this.positionCourante++;
        if (this.positionCourante == 4) {
            this.positionCourante = 0;
        }
        if (verifRotate(plateau) == false) {
            this.positionCourante = oldPosition;
        }
        while ((verifSortieAxeYG(this.getCoordCaseTetriminos())) == false) {
            moveRight(plateau);
        }
        while ((verifSortieAxeYD(this.getCoordCaseTetriminos(), plateau)) == false) {
            moveLeft(plateau);
        }
        while ((verifSortieAxeXB(this.getCoordCaseTetriminos(), plateau)) == false) {
            moveUp();
        }
    }

    public void rotateWithCoord() {
        boolean[][] tmp1 = new boolean[this.positionCourante+1][this.nBcolsGrille*this.nBlignesGrille];
        boolean[][] tmp2 = new boolean[this.nBlignesGrille][this.nBcolsGrille];
        int compt = 0;      
        for (int i = 0; i < this.nBlignesGrille; i++) {         
            for (int j = 0; j < this.nBcolsGrille; j++) {              
                tmp2[this.nBcolsGrille - j - 1][i] = TABTETRIMINO[this.positionCourante][compt];               
                compt++;
            }
        }
        compt=0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {         
                tmp1[this.positionCourante][compt] = tmp2[i][j];
                compt++;
              }
        }
        TABTETRIMINO = tmp1;
    }

    public void flipOver() {
        boolean[][] tmp2 = new boolean[this.nBlignesGrille][this.nBcolsGrille];
        boolean[][] tmp1 = new boolean[this.positionCourante+1][this.nBcolsGrille*this.nBlignesGrille];
        int compt = 0; 
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                tmp2[this.nBcolsGrille - i - 1][j] = TABTETRIMINO[this.positionCourante][compt];
                compt++;
            }
        }
        compt=0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {         
                tmp1[this.positionCourante][compt] = tmp2[i][j];
                compt++;
              }
        }
        TABTETRIMINO = tmp1;
    }

    private boolean verifSortieAxeYG(Case[][] cases) {
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                if ((this.getTABTETRIMINO()[this.positionCourante][compt] == true) && (cases[i][j].getCoord().getY() < 0)) {
                    return false;
                }
                compt++;
            }
        }
        return result;

    }

    private boolean verifSortieAxeYD(Case[][] cases, Plateau plateau) {
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                if ((this.getTABTETRIMINO()[this.positionCourante][compt] == true) && cases[i][j].getCoord().getY() >= plateau.getNbCols()) {
                    result = false;
                }
                compt++;
            }
        }
        return result;
    }

    private boolean verifSortieAxeXB(Case[][] cases, Plateau plateau) {
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                if ((this.getTABTETRIMINO()[this.positionCourante][compt] == true) && cases[i][j].getCoord().getX() >= plateau.getNbLignes()) {
                    return false;
                }
                compt++;
            }
        }
        return result;
    }

    public boolean verifMoveLeft(Plateau plateau) {
        //verifier qu'on ne sort pas en dehors du tableau
        boolean result = true;//par défaut on suppose qu'on peut bouger à gauche
        int compt = 0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                if (((this.coordCaseTetriminos[i][j].getCoord().getY() - 1) < 0) && (this.TABTETRIMINO[positionCourante][compt] == true)) {
                    return false;
                } else if (this.TABTETRIMINO[positionCourante][compt] == true && ((this.coordCaseTetriminos[i][j].getCoord().getY() - 1) < plateau.getNbCols()) && ((this.coordCaseTetriminos[i][j].getCoord().getY() - 1) >= 0)) {
                    if (true == (plateau.getGrille()[(this.getCoordCaseTetriminos()[i][j].getCoord().getX())][(this.getCoordCaseTetriminos()[i][j].getCoord().getY()) - 1])) {
                        return false;
                    }
                }
                compt++;
            }
        }
        return result;
    }

    public boolean verifMoveRight(Plateau plateau) {
        boolean result = true;//par défaut on suppose qu'on peut bouger à droite
        int compt = 0;
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                if (((this.coordCaseTetriminos[i][j].getCoord().getY() + 1) >= plateau.getNbCols()) && (this.TABTETRIMINO[positionCourante][compt] == true)) {
                    return false;
                } else if (((this.coordCaseTetriminos[i][j].getCoord().getY() + 1) >= 0) && ((this.coordCaseTetriminos[i][j].getCoord().getY() + 1) < plateau.getNbCols()) && this.TABTETRIMINO[positionCourante][compt] == true) {
                    if (true == (plateau.getGrille()[(this.getCoordCaseTetriminos()[i][j].getCoord().getX())][(this.getCoordCaseTetriminos()[i][j].getCoord().getY()) + 1])) {
                        return false;
                    }
                }
                compt++;
            }
        }
        return result;
    }

    public boolean verifMoveDown(Plateau plateau) {
        boolean result = true;//par défaut on suppose qu'on peut descendre
        int compt = 16;
        for (int i = this.nBlignesGrille - 1; i >= 0; i--) {
            for (int j = this.nBcolsGrille - 1; j >= 0; j--) {
                compt--;
                if (((this.getCoordCaseTetriminos()[i][j].getCoord().getX() + 1) < plateau.getNbLignes()) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) < plateau.getNbCols()) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && (this.TABTETRIMINO[positionCourante][compt] == true)) {
                    if (true == (plateau.getGrille()[(this.getCoordCaseTetriminos()[i][j].getCoord().getX()) + 1][(this.getCoordCaseTetriminos()[i][j].getCoord().getY())])) {
                        return false;
                    }
                } else if (((this.getCoordCaseTetriminos()[i][j].getCoord().getX() + 1) >= plateau.getNbLignes()) && (this.TABTETRIMINO[positionCourante][compt] == true)) {
                    return false;
                }
            }
        }
        return result;
    }

    public boolean verifRotate(Plateau plateau) {
        //recorriger les petites failles
        boolean result = true;
        int compt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) < plateau.getNbCols()) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getX()) < plateau.getNbLignes())) {
                    if (this.TABTETRIMINO[positionCourante][compt] == true) {
                        //si la case est a true et son index pointe hors du tableau vers la droite le décaler à l'intérieur                    
                        if (plateau.getGrille()[this.getCoordCaseTetriminos()[i][j].getCoord().getX()][this.getCoordCaseTetriminos()[i][j].getCoord().getY()] == true) {
                            return false;
                        }
                    }
                } else if (((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) < plateau.getNbCols()) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= 0) && ((this.getCoordCaseTetriminos()[i][j].getCoord().getX()) >= plateau.getNbLignes())) {
                    if (this.TABTETRIMINO[positionCourante][compt] == true) {
                        int diff = (this.getCoordCaseTetriminos()[i][j].getCoord().getX()) - plateau.getNbLignes();
                        //si la case est a true et son index pointe hors du tableau vers la droite le décaler à l'intérieur                    
                        if (plateau.getGrille()[this.getCoordCaseTetriminos()[i][j].getCoord().getX() - (diff + 1)][this.getCoordCaseTetriminos()[i][j].getCoord().getY()] == true) {
                            return false;
                        }
                    }
                } else if ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) < 0) {
                    if (this.TABTETRIMINO[positionCourante][compt] == true) {
                        int diff1 = abs(this.getCoordCaseTetriminos()[i][j].getCoord().getY());
                        int diff2 = 0;
                        //si la case est a true et son index pointe hors du tableau vers la droite le décaler à l'intérieur 
                        if (this.getCoordCaseTetriminos()[i][j].getCoord().getX() >= plateau.getNbLignes()) {
                            diff2 = (this.getCoordCaseTetriminos()[i][j].getCoord().getX()) - plateau.getNbLignes();
                            diff2++;
                        }
                        if (plateau.getGrille()[this.getCoordCaseTetriminos()[i][j].getCoord().getX() - (diff2)][this.getCoordCaseTetriminos()[i][j].getCoord().getY() + diff1] == true) {
                            return false;
                        }
                    }
                } else if ((this.getCoordCaseTetriminos()[i][j].getCoord().getY()) >= plateau.getNbCols()) {
                    if (this.TABTETRIMINO[positionCourante][compt] == true) {
                        int diff1 = this.getCoordCaseTetriminos()[i][j].getCoord().getY() - plateau.getNbCols();
                        int diff2 = 0;
                        //si la case est a true et son index pointe hors du tableau vers la droite le décaler à l'intérieur 
                        if (this.getCoordCaseTetriminos()[i][j].getCoord().getX() >= plateau.getNbLignes()) {
                            diff2 = (this.getCoordCaseTetriminos()[i][j].getCoord().getX()) - plateau.getNbLignes();
                            diff2++;
                        }
                        if (plateau.getGrille()[this.getCoordCaseTetriminos()[i][j].getCoord().getX() - (diff2)][this.getCoordCaseTetriminos()[i][j].getCoord().getY() - (diff1 + 1)] == true) {
                            return false;
                        }
                    }
                    compt++;
                }

            }

        }
        return result;
    }

    /**
     * @return the idTetrimino
     */
    public int getIdTetrimino() {
        return idTetrimino;
    }

    /**
     * @return the nomTetrimino
     */
    public String getNomTetrimino() {
        return nomTetrimino;
    }

    /**
     * @return the positionCourante
     */
    public int getPositionCourante() {
        return positionCourante;
    }

    /**
     * @param positionCourante the positionCourante to set
     */
    public void setPositionCourante(int positionCourante) {
        this.positionCourante = positionCourante;
    }

    /**
     * @return the TABTETRIMINO
     */
    public boolean[][] getTABTETRIMINO() {
        return TABTETRIMINO;
    }

    /**
     * @return the coordCaseTetriminos
     */
    public Case[][] getCoordCaseTetriminos() {
        return coordCaseTetriminos;
    }

    /**
     * @param coordCaseTetriminos the coordCaseTetriminos to set
     */
    public void setCoordCaseTetriminos(Case[][] coordCaseTetriminos) {
        this.coordCaseTetriminos = coordCaseTetriminos;
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

    public void initCoord() {
        for (int i = 0; i < this.nBlignesGrille; i++) {
            for (int j = 0; j < this.nBcolsGrille; j++) {
                this.coordCaseTetriminos[i][j] = new Case();
                this.coordCaseTetriminos[i][j].setCoord(new Coordonnees(i, j));
            }
        }
    }
}
