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
public class Case {
    private Coordonnees coord;
    
    /**
     * @return the coord
     */
    public Case(Coordonnees coord){
    this.coord=coord;
    }
    public Case (){
    this.coord=null;
    }
    public Coordonnees getCoord() {
        return coord;
        //TetriminoI t = new TetriminoI();
        //t.
    }

    /**
     * @param coord the coord to set
     */
    public void setCoord(Coordonnees coord) {
        this.coord = coord;
    }
    
    
}
