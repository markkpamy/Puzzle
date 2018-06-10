package ViewController;

import Comm.Communication;
import Grille.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
        agentMap.values().forEach(agent -> {
            view.getText()[agent.getCurrentCase().getPosition().getX()][agent.getCurrentCase().getPosition().getY()].setText(String.valueOf(agent.getIdAgent()));
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
                    puzzleView.getText()[tmp.getPosition().getX()][tmp.getPosition().getY()].setText(tmp.getText());
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = true;
                } else if (puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()]) {
                    puzzleView.getRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()].setFill(Color.BLACK);
                    puzzleView.getText()[tmp.getPosition().getX()][tmp.getPosition().getY()].setText("");
                    puzzleView.getColoredRectPan()[tmp.getPosition().getX()][tmp.getPosition().getY()] = false;
                }
            }
        });
    }

    private static void setAgents(Plateau plateau) {
        AgentFactory agentFactory = new AgentFactory(plateau.getNbCols(), plateau.getNbLignes());
        Map<Integer, Agent> map = agentFactory.createMultiple(10);
//        Agent mark = new Agent(1, "Mark", new Case(new Position(2, 6)), Agent.Color.RED);
//        Agent martial = new Agent(2, "Martial", new Case(new Position(1, 4)), Agent.Color.BLUE);
//        Agent fabien = new Agent(3, "Fabien", new Case(new Position(7, 3)), Agent.Color.GREEN);
//        Agent aknine = new Agent(4, "Aknine", new Case(new Position(8, 2)), Agent.Color.YELLOW);
//        Agent hulk = new Agent(5, "Hulk", new Case(new Position(1, 5)), Agent.Color.CYAN);
//        Agent cptAmerica = new Agent(6, "cptAmerica", new Case(new Position(3, 1)), Agent.Color.ORANGE);
//
//        Agent mark2 = new Agent(7, "Mark2", new Case(new Position(6, 2)), Agent.Color.RED);
//        Agent martial2 = new Agent(8, "Martial2", new Case(new Position(4, 1)), Agent.Color.BLUE);
//        Agent fabien2 = new Agent(9, "Fabien", new Case(new Position(3, 7)), Agent.Color.GREEN);
//        Agent aknine2 = new Agent(10, "Aknine", new Case(new Position(2, 8)), Agent.Color.YELLOW);
//        Agent hulk2 = new Agent(11, "Hulk", new Case(new Position(5, 1)), Agent.Color.CYAN);
//        Agent cptAmerica2 = new Agent(12, "cptAmerica", new Case(new Position(1, 3)), Agent.Color.ORANGE);
//        map.put(martial.getIdAgent(), martial);
//        map.put(fabien.getIdAgent(), fabien);
//        map.put(aknine.getIdAgent(), aknine);
//        map.put(mark.getIdAgent(), mark);
//        map.put(hulk.getIdAgent(), hulk);
//        map.put(cptAmerica.getIdAgent(), cptAmerica);
//
//        map.put(martial2.getIdAgent(), martial2);
//        map.put(fabien2.getIdAgent(), fabien2);
//        map.put(aknine2.getIdAgent(), aknine2);
//        map.put(mark2.getIdAgent(), mark2);
//        map.put(hulk2.getIdAgent(), hulk2);
//        map.put(cptAmerica2.getIdAgent(), cptAmerica2);
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

