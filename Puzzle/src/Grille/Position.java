/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;
 
/**
 *
 * @author markk
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
    this.x=x;
    this.y=y;
    }
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the j
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the j to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public double getDistance(Position position) {
        return Math.sqrt(Math.pow(position.getX() - this.x, 2) + Math.pow(position.getY() - this.y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        Position tmp = (Position) obj;
        return tmp.x == this.x && tmp.y == this.y;
    }

    @Override
    public String toString() {
        return "{Position: ("+x+", "+y+")}";
    }
}
