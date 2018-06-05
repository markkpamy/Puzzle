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
    private Position position;
    
    /**
     * @return the position
     */
    public Case(Position coord){
    this.position =coord;
    }
    public Case (){
    this.position =null;
    }
    public Position getPosition() {
        return position;
        //TetriminoI t = new TetriminoI();
        //t.
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    
}
