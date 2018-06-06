package ViewController;

import Grille.*;

import java.util.HashMap;
import java.util.Map;

public class PuzzleGameCore {

    public static void play(Plateau plateau, PanView view) {
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
        agentMap.values().forEach(agent -> {
            Thread thread = new Thread(agent);
            thread.start();
        });
    }

    private static void setAgents(Plateau plateau) {
        Map<Integer, Agent> map = new HashMap<>();
        Agent mark = new Agent(1, "Mark", new Case(new Position(2, 6)), Agent.Color.RED);
        map.put(mark.getIdAgent(), mark);
        Agent martial = new Agent(2, "Martial", new Case(new Position(1, 4)), Agent.Color.BLUE);
        Agent fabien = new Agent(3, "Fabien", new Case(new Position(7, 3)), Agent.Color.GREEN);
        Agent aknine = new Agent(4, "Aknine", new Case(new Position(8, 2)), Agent.Color.YELLOW);
        map.put(martial.getIdAgent(), martial);
        map.put(fabien.getIdAgent(), fabien);
        map.put(aknine.getIdAgent(), aknine);
        plateau.setAgentMap(map);
    }

}

