package ViewController;

import Grille.Agent;
import Grille.Plateau;

public class PuzzleGameCore {

    public static void updatePlateauColors(Agent t, Agent.Color[][] colorsTable) {
        colorsTable[t.getaCase().getCoord().getX()][t.getaCase().getCoord().getY()] = t.getColor();
    }


    public static void moveUp(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveUp(plateau);
        updatePlateauColors(plateau.getCurrentAgent(), rectPlateau);
        plateau.displayPiece(plateau.getCurrentAgent());
    }

    public static void moveLeft(Plateau plateau, Agent.Color[][] rectPlateau) {

        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveLeft(plateau);
        updatePlateauColors(plateau.getCurrentAgent(), rectPlateau);
        plateau.displayPiece(plateau.getCurrentAgent());

    }

    public static void moveRight(Plateau plateau, Agent.Color[][] rectPlateau) {

        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveRight(plateau);
        updatePlateauColors(plateau.getCurrentAgent(), rectPlateau);
        plateau.displayPiece(plateau.getCurrentAgent());

    }

    public static void moveDown(Plateau plateau, Agent.Color[][] rectPlateau) {
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveDown(plateau);
        updatePlateauColors(plateau.getCurrentAgent(), rectPlateau);
        plateau.displayPiece(plateau.getCurrentAgent());
    }

}

