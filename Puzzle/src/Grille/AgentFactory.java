package Grille;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AgentFactory {
    private int idAgent = 1;
    private int nbColumns;
    private int nbRows;
    private HashSet<Position> startPositionsUsed;
    private HashSet<Position> goalPositionsUsed;
    private ArrayList<Position> freeStartPositions = new ArrayList<>();
    private ArrayList<Position> freeEndPositions = new ArrayList<>();

    public AgentFactory(int nbColonne, int nbLigne) {
        this.nbColumns = nbColonne;
        this.nbRows = nbLigne;
        startPositionsUsed = new HashSet<>();
        goalPositionsUsed = new HashSet<>();
        for (int x=0; x<= nbRows -1; x++) {
            for (int y=0; y<= nbRows -1; y++) {
                Position position = new Position(x,y);
                freeStartPositions.add(position);
                freeEndPositions.add(position);

            }
        }
    }

    public Agent create() {
        Position startPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(0, nbColumns - 1);
            int y = ThreadLocalRandom.current().nextInt(0, nbRows - 1);
            startPosition = new Position(x, y);
        } while (startPositionsUsed.contains(startPosition));
        Position goalPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(0, nbColumns - 1);
            int y = ThreadLocalRandom.current().nextInt(0, nbRows - 1);
            goalPosition = new Position(x, y);
        } while (goalPositionsUsed.contains(goalPosition) || goalPosition.equals(startPosition));
        Agent.Color color = Agent.Color.BLUE;
        Agent agent = new Agent(idAgent, "", new Case(startPosition), new Case(goalPosition), color);
        startPositionsUsed.add(startPosition);
        goalPositionsUsed.add(goalPosition);
        idAgent++;
        System.out.println(agent);
        return agent;
    }

    public Agent createAlternative() {
        Position startPosition;
        Position endPosition;
        do {
            int iStart = ThreadLocalRandom.current().nextInt(0, freeStartPositions.size() - 1);
            int iEnd = ThreadLocalRandom.current().nextInt(0, freeEndPositions.size() - 1);
            startPosition = freeStartPositions.get(iStart);
            endPosition = freeEndPositions.get(iEnd);
        } while (startPosition.equals(endPosition));

        Agent.Color color = Agent.Color.BLUE;
        Agent agent = new Agent(idAgent, "", new Case(startPosition), new Case(endPosition), color);
        freeStartPositions.remove(startPosition);
        freeEndPositions.remove(endPosition);
        idAgent++;
        System.out.println(agent);
        return agent;
    }

    public Map<Integer, Agent> createMultiple(int nbAgent) {
        Map<Integer, Agent> agentMap = new HashMap<>();
        int limit = nbColumns * nbRows;
        for (int i = 0; i < nbAgent; i++) {
            if (i == limit){
                return agentMap;
            }
            Agent agent = createAlternative();
            agentMap.put(agent.getIdAgent(), agent);
        }
        return agentMap;
    }
}
