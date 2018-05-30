/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModuleGestionPlateau;
 
/**
 *
 * @author markk
 */
public class Coordonnees {
    private int x;
    private int y;

    public Coordonnees(int x, int y){
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
    
}
