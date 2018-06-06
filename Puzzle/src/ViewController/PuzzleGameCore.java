package ViewController;

import Comm.Communication;
import Grille.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class PuzzleGameCore {

    public static void play(PanView view) {
        Plateau plateau = new Plateau(10, 10);
        plateau.setNaturalLanguageColors(view.getNaturalLanguageColors());
        setObserver(plateau, view);
        plateau.clearPlateau();
        setAgents(plateau);
        Map<Integer, Agent> agentMap =  plateau.getAgentMap();
        agentMap.values().forEach(agent -> {
            view.getNaturalLanguageColors()[agent.getCurrentCase().getPosition().getX()][agent.getCurrentCase().getPosition().getY()] = agent.getColor();
        });
        plateau.displayPieces();
        agentMap.values().forEach(agent -> {
            agent.setPlateau(plateau);
        });
        agentMap.values().forEach(agent -> { new Thread(agent).start();
        });
    }

    private static void setObserver(Plateau plateau, PanView puzzleView) {
        plateau.addObserver((Observable o, Object arg) -> {
            if (arg instanceof Case) {
                Case tmp = (Case) arg;
                if (!puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                    puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(convertColor(puzzleView.getNaturalLanguageColors()[tmp.getPosition().getX()][tmp.getPosition().getY()]));
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = true;
                } else if (puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                    puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(Color.BLACK);
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = false;
                }
            }
        });
    }

    private static void setAgents(Plateau plateau) {
        Map<Integer, Agent> map = new HashMap<>();
        Agent mark = new Agent(1, "Mark", new Case(new Position(2, 6)), Agent.Color.RED);
        Agent martial = new Agent(2, "Martial", new Case(new Position(1, 4)), Agent.Color.BLUE);
        Agent fabien = new Agent(3, "Fabien", new Case(new Position(7, 3)), Agent.Color.GREEN);
        Agent aknine = new Agent(4, "Aknine", new Case(new Position(8, 2)), Agent.Color.YELLOW);
        map.put(martial.getIdAgent(), martial);
        map.put(fabien.getIdAgent(), fabien);
        map.put(aknine.getIdAgent(), aknine);
        map.put(mark.getIdAgent(), mark);
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

