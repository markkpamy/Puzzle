package ViewController;

import Grille.Agent;
import Grille.Plateau;

public class PuzzleGameCore {

    public static void updatePlateauColors(Agent t, Agent.Color[][] colorsTable) {
        colorsTable[t.getCurrentCase().getPosition().getX()][t.getCurrentCase().getPosition().getY()] = t.getColor();
    }


    public static void moveUp(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveUp(plateau);
        plateau.updatePlateau(plateau.getCurrentAgent());
    }

    public static void moveLeft(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveLeft(plateau);
        plateau.updatePlateau(plateau.getCurrentAgent());
    }

    public static void moveRight(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveRight(plateau);
        plateau.updatePlateau(plateau.getCurrentAgent());

    }

    public static void moveDown(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveDown(plateau);
        plateau.updatePlateau(plateau.getCurrentAgent());
    }

}

