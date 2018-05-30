package ViewController;

import Grille.Agent;
import Grille.Plateau;

public class PuzzleGameCore {

    Agent currentAgent ;


    public static void moveUp(Plateau plateau){
        plateau.effaceTracePiece(plateau.getCurrentAgent());
        plateau.getCurrentAgent().moveUp(plateau);
        // TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
        plateau.displayPiece(plateau.getCurrentAgent());
    }

    public static void moveLeft(Plateau plateau) {

            plateau.effaceTracePiece(plateau.getCurrentAgent());
            plateau.getCurrentAgent().moveLeft(plateau);
           // TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentAgent());

    }

    public static void moveRight(Plateau plateau) {

            plateau.effaceTracePiece(plateau.getCurrentAgent());
            plateau.getCurrentAgent().moveRight(plateau);
            //TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentAgent());

    }

    public static void moveDown(Plateau plateau, Agent.Couleur[][] couleursRectPlateau) {
            plateau.effaceTracePiece(plateau.getCurrentAgent());
            plateau.getCurrentAgent().moveDown(plateau);
            //TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentAgent());
        }

    }

