package ViewController;

import Grille.Agent;
import Grille.Plateau;

public class PuzzleGameCore {

    Agent currentAgent ;


    public static void moveUp(Plateau plateau, ){

    }

    public static void moveLeft(Plateau plateau, Tetrimino.Couleur[][] couleursRectPlateau) {
        if (launched == true && paused == false && moveDownFinished == true) {
            plateau.effaceTracePiece(plateau.getCurrentTetrimino());
            plateau.getCurrentTetrimino().moveLeft(plateau);
            TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentTetrimino());
        }
    }

    public static void moveRight(Plateau plateau, Tetrimino.Couleur[][] couleursRectPlateau) {
        if (launched == true && paused == false && moveDownFinished == true) {
            plateau.effaceTracePiece(plateau.getCurrentTetrimino());
            plateau.getCurrentTetrimino().moveRight(plateau);
            TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentTetrimino());
        }
    }

    public static void moveDown(Plateau plateau, Tetrimino.Couleur[][] couleursRectPlateau) {
        if (launched == true && paused == false) {
            plateau.effaceTracePiece(plateau.getCurrentTetrimino());
            plateau.getCurrentTetrimino().moveDown(plateau);
            TetrisGameCore.copyRectPlateauColors(plateau.getCurrentTetrimino(), couleursRectPlateau);
            plateau.displayPiece(plateau.getCurrentTetrimino());
        }

    }
}
