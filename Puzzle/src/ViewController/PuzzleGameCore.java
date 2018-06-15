package ViewController;

import Comm.Communication;
import Grille.*;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

public class PuzzleGameCore {

    public static void play(PanView gameView, PanView finalStateView, int nbAgents, int rows, int columns) {
        Plateau plateau = new Plateau(rows, columns);
        Plateau finalState = new Plateau(rows, columns);
        plateau.setNaturalLanguageColors(gameView.getNaturalLanguageColors());
        finalState.setNaturalLanguageColors(finalStateView.getNaturalLanguageColors());
        setObserver(plateau, gameView);
        setObserver(finalState, finalStateView);
        plateau.clearPlateau();
        finalState.clearPlateau();
        setAgents(plateau, nbAgents);
        Map<Integer, Agent> agentMap = plateau.getAgentMap();
        agentMap.values().forEach(agent -> {
            gameView.getNaturalLanguageColors()[agent.getCurrentCase().getPosition().getX()][agent.getCurrentCase().getPosition().getY()] = agent.getColor();
        });
        agentMap.values().forEach(agent -> {
            gameView.getText()[agent.getCurrentCase().getPosition().getX()][agent.getCurrentCase().getPosition().getY()].setText(String.valueOf(agent.getIdAgent()));
        });
        updateFinalStateView(finalState,agentMap);
        plateau.displayPieces();
        agentMap.values().forEach(agent -> {
            agent.setPlateau(plateau);
        });
        agentMap.values().forEach(agent -> {
            new Thread(agent).start();
        });
    }

    private static void setObserver(Plateau plateau, PanView puzzleView) {

        plateau.addObserver((Observable o, Object arg) -> {
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (arg instanceof Case) {
                                          Case tmp = (Case) arg;
                                          if (!puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                                              puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(convertColor(puzzleView.getNaturalLanguageColors()[tmp.getPosition().getX()][tmp.getPosition().getY()]));
                                              puzzleView.getText()[tmp.getPosition().getX()][tmp.getPosition().getY()].setText(tmp.getText());
                                              puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = true;
                                          } else if (puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                                              puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(Color.BLACK);
                                              puzzleView.getText()[tmp.getPosition().getX()][tmp.getPosition().getY()].setText("");
                                              puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = false;
                                          }
                                      }
                                  }
                              }

            );

        });
    }

    public static void updateFinalStateView(Plateau plateau, Map<Integer, Agent> agentMap) {
        agentMap.values().forEach(agent -> {
            plateau.getNaturalLanguageColors()[agent.getGoalCase().getPosition().getX()][agent.getGoalCase().getPosition().getY()] = agent.getColor();
            plateau.getPanCases()[agent.getGoalCase().getPosition().getX()][agent.getGoalCase().getPosition().getY()].setText(String.valueOf(agent.getIdAgent()));
            plateau.setGrilleCaseTrue(agent.getGoalCase().getPosition().getX(), agent.getGoalCase().getPosition().getY());
        });
    }

    private static void setAgents(Plateau plateau, int nbAgents) {
        AgentFactory agentFactory = new AgentFactory(plateau.getNbCols(), plateau.getNbLignes());
        Map<Integer, Agent> map = agentFactory.createMultiple(nbAgents);
        Communication.getInstance().setCommunication(new ArrayList<Agent>(map.values()));
        plateau.setAgentMap(map);
    }

    private static Color convertColor(Agent.Color couleur) {
        switch (couleur) {
            case CYAN:
                return Color.CYAN;
            case RED:
                return Color.RED;
            case GREEN:
                return Color.GREEN;
            case PURPLE:
                return Color.PURPLE;
            case ORANGE:
                return Color.ORANGE;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
        }
        return Color.BLANCHEDALMOND;
    }

}

