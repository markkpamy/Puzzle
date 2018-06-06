package ViewController;

import Grille.*;

import java.util.HashMap;
import java.util.Map;

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
            thread.run();
        });
    }

    private static void setAgents(Plateau plateau) {
        Map<Integer, Agent> map = new HashMap<>();
        Agent mark = new Agent(1, "Mark", new Case(new Position(2, 6)), Agent.Color.RED);
        Agent martial = new Agent(2, "Martial", new Case(new Position(1, 4)), Agent.Color.BLUE);
        Agent fabien = new Agent(3, "Fabien", new Case(new Position(7, 3)), Agent.Color.GREEN);
        Agent aknine = new Agent(4, "Aknine", new Case(new Position(8, 2)), Agent.Color.YELLOW);
        map.put(mark.getIdAgent(), mark);
//        map.put(martial.getIdAgent(), martial);
//        map.put(fabien.getIdAgent(), fabien);
//        map.put(aknine.getIdAgent(), aknine);
        plateau.setAgentMap(map);
    }

}

