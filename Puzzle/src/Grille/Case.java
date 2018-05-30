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
    private Position coord;
    
    /**
     * @return the coord
     */
    public Case(Position coord){
    this.coord=coord;
    }
    public Case (){
    this.coord=null;
    }
    public Position getCoord() {
        return coord;
        //TetriminoI t = new TetriminoI();
        //t.
    }

    /**
     * @param coord the coord to set
     */
    public void setCoord(Position coord) {
        this.coord = coord;
    }
    
    
}
