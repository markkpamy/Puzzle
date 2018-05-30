/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;


import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author markk
 */
public class VuePlateau {

    /**
     * @return the rectPlateau
     */
    public Rectangle[][] getRectPlateau() {
        return rectPlateau;
    }

    /**
     * @param rectPlateau the rectPlateau to set
     */
    public void setRectPlateau(Rectangle[][] rectPlateau) {
        this.rectPlateau = rectPlateau;
    }

    /**
     * @return the colouredRectPlateau
     */
    public boolean[][] getColouredRectPlateau() {
        return colouredRectPlateau;
    }

    /**
     * @param colouredRectPlateau the colouredRectPlateau to set
     */
    public void setColouredRectPlateau(boolean[][] colouredRectPlateau) {
        this.colouredRectPlateau = colouredRectPlateau;
    }

    /**
     * @return the colorsRectPlateau
     */
    public Color[][] getColorsRectPlateau() {
        return colorsRectPlateau;
    }

    /**
     * @param colorsRectPlateau the colorsRectPlateau to set
     */
    public void setColorsRectPlateau(Color[][] colorsRectPlateau) {
        this.colorsRectPlateau = colorsRectPlateau;
    }

    /**
     * @return the couleursRectPlateau
     */
    public Agent.Couleur[][] getCouleursRectPlateau() {
        return couleursRectPlateau;
    }

    /**
     * @param couleursRectPlateau the couleursRectPlateau to set
     */
    public void setCouleursRectPlateau(Agent.Couleur[][] couleursRectPlateau) {
        this.couleursRectPlateau = couleursRectPlateau;
    }

    private GridPane gPane2 = new GridPane();
    private Rectangle[][] rectPlateau;
    private boolean[][] colouredRectPlateau;
    private Color[][] colorsRectPlateau;
    private Agent.Couleur[][] couleursRectPlateau;
   

    public VuePlateau(int nBlignes, int nBcols) {
        rectPlateau = new Rectangle[nBlignes][nBcols];
        colouredRectPlateau = new boolean[nBlignes][nBcols];
        colorsRectPlateau = new Color[nBlignes][nBcols];
        couleursRectPlateau = new Agent.Couleur[nBlignes][nBcols];
        drawView(nBlignes,nBcols);
    }

    public void drawView(int nBlignes, int nBcols) {
        for (int i = 0; i < nBlignes; i++) {
            for (int j = 0; j < nBcols; j++) {
                getRectPlateau()[i][j] = new Rectangle(i, j, 30, 25);
                getRectPlateau()[i][j].setFill(Color.BLACK);
                getRectPlateau()[i][j].setArcWidth(10.0);
                getRectPlateau()[i][j].setArcHeight(10.0);
                getColouredRectPlateau()[i][j] = false;
                getColorsRectPlateau()[i][j] = Color.BLACK;
                getRectPlateau()[i][j].setStroke(Color.WHITE);
                getgPane2().add(getRectPlateau()[i][j], j, i);
            }
        }
    }

    /**
     * @return the gPane2
     */
    public GridPane getgPane2() {
        return gPane2;
    }

    /**
     * @param gPane2 the gPane2 to set
     */
    public void setgPane2(GridPane gPane2) {
        this.gPane2 = gPane2;
    }

    
}
