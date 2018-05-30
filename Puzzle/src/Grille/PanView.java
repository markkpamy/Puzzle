/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;


import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author markk
 */
public class PanView {

    /**
     * @return the rectPan
     */
    public Rectangle[][] getRectPan() {
        return rectPan;
    }

    /**
     * @param rectPan the rectPan to set
     */
    public void setRectPan(Rectangle[][] rectPan) {
        this.rectPan = rectPan;
    }

    /**
     * @return the coloredRectPan
     */
    public boolean[][] getColoredRectPan() {
        return coloredRectPan;
    }

    /**
     * @param coloredRectPan the coloredRectPan to set
     */
    public void setColoredRectPan(boolean[][] coloredRectPan) {
        this.coloredRectPan = coloredRectPan;
    }

    /**
     * @return the colorsRectPan
     */
    public javafx.scene.paint.Color[][] getColorsRectPan() {
        return colorsRectPan;
    }

    /**
     * @param colorsRectPan the colorsRectPan to set
     */
    public void setColorsRectPan(javafx.scene.paint.Color[][] colorsRectPan) {
        this.colorsRectPan = colorsRectPan;
    }

    /**
     * @return the colorsRectPans
     */
    public Agent.Color[][] getColorsRectPans() {
        return colorsRectPans;
    }

    /**
     * @param colorsRectPans the colorsRectPans to set
     */
    public void setColorsRectPans(Agent.Color[][] colorsRectPans) {
        this.colorsRectPans = colorsRectPans;
    }

    private GridPane gPane2 = new GridPane();
    private Rectangle[][] rectPan;
    private boolean[][] coloredRectPan;
    private javafx.scene.paint.Color[][] colorsRectPan;
    private Agent.Color[][] colorsRectPans;
   

    public PanView(int nBlignes, int nBcols) {
        rectPan = new Rectangle[nBlignes][nBcols];
        coloredRectPan = new boolean[nBlignes][nBcols];
        colorsRectPan = new javafx.scene.paint.Color[nBlignes][nBcols];
        colorsRectPans = new Agent.Color[nBlignes][nBcols];
        drawView(nBlignes,nBcols);
    }

    public void drawView(int nBlignes, int nBcols) {
        for (int i = 0; i < nBlignes; i++) {
            for (int j = 0; j < nBcols; j++) {
                getRectPan()[i][j] = new Rectangle(i, j, 30, 25);
                getRectPan()[i][j].setFill(javafx.scene.paint.Color.BLACK);
                getRectPan()[i][j].setArcWidth(10.0);
                getRectPan()[i][j].setArcHeight(10.0);
                getColoredRectPan()[i][j] = false;
                getColorsRectPan()[i][j] = javafx.scene.paint.Color.BLACK;
                getRectPan()[i][j].setStroke(javafx.scene.paint.Color.WHITE);
                getgPane2().add(getRectPan()[i][j], j, i);
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