/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import Grille.Agent.Color;
import javafx.scene.text.Text;

/**
 * @author markk
 */
public class Plateau extends Observable {

    private final int nbLignes;
    private final int nbCols;
    private boolean[][] grille;
    private final Case[][] panCases;
    private Agent.Color[][] rectPlateau;
    private Agent currentAgent;
    private Map<Integer, Agent> agentMap;
    private Agent.Color naturalLanguageColors[][];

    public Plateau(int nbLignes, int nbCols) {
        this.nbCols = nbCols;
        this.nbLignes = nbLignes;
        this.panCases = new Case[nbLignes][nbCols];
        this.grille = new boolean[nbLignes][nbCols];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbCols; j++) {
                this.grille[i][j] = false;
                this.panCases[i][j] = new Case(new Position(i, j));
            }
        }
        agentMap = new HashMap<>();
        this.currentAgent = new Agent();
        rectPlateau = new Agent.Color[nbLignes][nbCols];
    }

    public void displayPieces() {

        agentMap.values().forEach(this::updatePlateau);
    }

    public synchronized boolean displayPiece(Agent t) {
        boolean result = true;
        if (!this.grille[t.getCurrentCase().getPosition().getX()][t.getCurrentCase().getPosition().getY()]) {
            setGrilleCaseTrue(t.getCurrentCase().getPosition().getX(), t.getCurrentCase().getPosition().getY());
        } else {
            result = false;
        }
        t.setPlateau(this);
        return result;
    }

    public Agent findAgent(Position position) {
        Optional<Map.Entry<Integer, Agent>> optionalAgent = agentMap.entrySet().stream()
                .filter(agentEntry -> position.equals(agentEntry.getValue().getCurrentCase().getPosition()))
                .findFirst();
        return optionalAgent.isPresent() ? optionalAgent.get().getValue() : null;
    }


    public void clearPlateau() {
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbCols; j++) {
                setGrilleCaseFalse(i, j);
            }
        }
    }

    public synchronized void updatePlateau(Agent t) {
        naturalLanguageColors[t.getCurrentCase().getPosition().getX()][t.getCurrentCase().getPosition().getY()] = t.getColor();
        panCases[t.getCurrentCase().getPosition().getX()][t.getCurrentCase().getPosition().getY()].setText(String.valueOf(t.getIdAgent()));
        this.displayPiece(t);
    }

    public synchronized void setGrilleCaseTrue(int i, int j) {
        if (!this.grille[i][j]) {
            this.grille[i][j] = true;
            setChanged();
            notifyObservers(getCoordCasesGrille()[i][j]);
        }
    }

    //public void displayPiece(Tetrimino t){
    public synchronized void setGrilleCaseFalse(int i, int j) {
        if (this.grille[i][j]) {
            this.grille[i][j] = false;
            setChanged();
            notifyObservers(getCoordCasesGrille()[i][j]);
        }
    }

    //}

    /**
     * @param t
     */
    public synchronized void  effaceTracePiece(Agent t) {
        if (this.grille[t.getCurrentCase().getPosition().getX()][t.getCurrentCase().getPosition().getY()]) {
            setGrilleCaseFalse(t.getCurrentCase().getPosition().getX(), t.getCurrentCase().getPosition().getY());
        }
    }


    public boolean[][] getGrille() {
        return grille;
    }

    /*K*
     * @param grille the grille to set
     */
    public void setGrille(boolean[][] grille) {
        this.grille = grille;
    }

    /**
     * @return the coordCasesGrille
     */
    public Case[][] getCoordCasesGrille() {
        return panCases;
    }

    /**
     * @return the currentTetrimino
     */
    public Agent getCurrentAgent() {
        return currentAgent;
    }

    /**
     * @param currentAgent the currentTetrimino to set
     */
    public void setCurrentAgent(Agent currentAgent) {
        this.currentAgent = currentAgent;
    }

    /**
     * @return the nbLignes
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * @return the nbCols
     */
    public int getNbCols() {
        return nbCols;
    }


    /**
     * @return the rectPlateau
     */
    public Color[][] getRectPlateau() {
        return rectPlateau;
    }

    /**
     * @param rectPlateau the rectPlateau to set
     */
    public void setRectPlateau(Color[][] rectPlateau) {
        this.rectPlateau = rectPlateau;
    }

    public Case[][] getPanCases() {
        return panCases;
    }

    public Map<Integer, Agent> getAgentMap() {
        return agentMap;
    }

    public void setAgentMap(Map<Integer, Agent> agentMap) {
        this.agentMap = agentMap;
        agentMap.forEach(((integer, agent) -> {
            agent.setPlateau(this);
        }));
    }

    public Color[][] getNaturalLanguageColors() {
        return naturalLanguageColors;
    }

    public void setNaturalLanguageColors(Color[][] naturalLanguageColors) {
        this.naturalLanguageColors = naturalLanguageColors;
    }

    public boolean isFinished() {
        return agentMap.values().stream().allMatch(Agent::isGoalReached);
    }

    public boolean isPositionAvailable(Position position) {
        if (position.getX()>=this.nbLignes || position.getY()>=this.nbCols|| position.getX()< 0|| position.getY()< 0) {
            return false;
        }
        return !this.grille[position.getX()][position.getY()];
    }
}
