/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import javafx.scene.text.Text;

/**
 *
 * @author markk
 */
public class Case {
    private Position position;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    
    /**
     * @return the position
     */
    public Case(Position coord){
    this.position =coord;
    }


    public Case (){
    this.position =null;
    this.text = "";
    }
    public Position getPosition() {
        return position;
    }


    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    
}
