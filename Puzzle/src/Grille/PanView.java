/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author markk
 */
public class PanView {
    private GridPane gPane2 = new GridPane();
    private Rectangle[][] rectPan;
    private boolean[][] coloredRectPan;
    private Text[][] text ;
    private javafx.scene.paint.Color[][] fxColorsRectPan;
    private Agent.Color naturalLanguageColors[][];
    private int rows;
    private int columns;


    public PanView(int nBlignes, int nBcols) {
        this.rows = nBlignes;
        this.columns = nBcols;
        rectPan = new Rectangle[nBlignes][nBcols];
        text = new Text[nBlignes][nBcols];
        /*
        */
        coloredRectPan = new boolean[nBlignes][nBcols];
        fxColorsRectPan = new javafx.scene.paint.Color[nBlignes][nBcols];
        naturalLanguageColors = new Agent.Color[nBlignes][nBcols];
        drawView(nBlignes,nBcols);
    }

    public void drawView(int nBlignes, int nBcols) {
        for (int i = 0; i < nBlignes; i++) {
            for (int j = 0; j < nBcols; j++) {
                StackPane stack = new StackPane();
                text[i][j] = new Text("");
                text[i][j].setFill(Color.WHITE);
                text[i][j].setFont(Font.font(null, FontWeight.BOLD, 15));
                getRectPan()[i][j] = new Rectangle(i, j, 30, 25);
                getRectPan()[i][j].setFill(javafx.scene.paint.Color.BLACK);
                getRectPan()[i][j].setArcWidth(10.0);
                getRectPan()[i][j].setArcHeight(10.0);
                getColoredRectPan()[i][j] = false;
                getFxColorsRectPan()[i][j] = javafx.scene.paint.Color.BLACK;
                getRectPan()[i][j].setStroke(javafx.scene.paint.Color.WHITE);
                stack.getChildren().addAll(getRectPan()[i][j],text[i][j]);
                getgPane2().add(stack, j, i );

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
    public javafx.scene.paint.Color[][] getFxColorsRectPan() {
        return fxColorsRectPan;
    }

    /**
     * @param colorsRectPan the colorsRectPan to set
     */
    public void setFxColorsRectPan(javafx.scene.paint.Color[][] colorsRectPan) {
        this.fxColorsRectPan = colorsRectPan;
    }

    /**
     * @return the colorsRectPans
     */
    public Agent.Color[][] getNaturalLanguageColors() {
        return naturalLanguageColors;
    }

    /**
     * @param colorsRectPans the colorsRectPans to set
     */
    public void setNaturalLanguageColors(Agent.Color[][] colorsRectPans) {
        this.naturalLanguageColors = colorsRectPans;
    }

    public Text[][] getText() {
        return text;
    }

    public void setText(Text[][] text) {
        this.text = text;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
