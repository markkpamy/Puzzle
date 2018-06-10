package Grille;

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

    public AgentFactory(int nbColonne, int nbLigne) {
        this.nbColumns = nbColonne;
        this.nbRows = nbLigne;
        startPositionsUsed = new HashSet<>();
        goalPositionsUsed = new HashSet<>();
    }

    public Agent create() {
        Position startPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(0, nbColumns);
            int y = ThreadLocalRandom.current().nextInt(0, nbRows);
            startPosition = new Position(x, y);
        } while (startPositionsUsed.contains(startPosition));
        Position goalPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(0, nbColumns);
            int y = ThreadLocalRandom.current().nextInt(0, nbRows);
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

    public Map<Integer, Agent> createMultiple(int nbAgent) {
        Map<Integer, Agent> agentMap = new HashMap<>();
        for (int i = 0; i < nbAgent; i++) {
            Agent agent = create();
            agentMap.put(agent.getIdAgent(), agent);
        }
        return agentMap;
    }
}
