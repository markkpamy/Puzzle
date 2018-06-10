package Grille;

import java.util.*;

public class ShorthestPast {
    /**
     * ressources : https://gist.github.com/raymondchua/8064159
     */
    private int cout;
    private List<Position> way;
    private Agent agent;
    private boolean[][] grille;

    public static Queue<Position> aStar(Agent agent, Plateau plateau) {
        HashSet<Node> explored = new HashSet<>();
        Position goal = agent.getGoalCase().getPosition();
        Position start = agent.getCurrentCase().getPosition();
        PriorityQueue<Node> queue = new PriorityQueue<Node>(plateau.getNbCols(), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getPosition().getDistance(goal) > o2.getPosition().getDistance(goal)) {
                    return 1;
                } else if (o1.getPosition().getDistance(goal) < o2.getPosition().getDistance(goal)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        boolean found = false;
        double cost = 0;
        Node startNode = new Node(start);
        startNode.setG_score(cost);
        queue.add(startNode);
        Node current = null;
        while ((!queue.isEmpty()) && !found) {
            current = queue.poll();

            explored.add(current);
            //goal found
            if (current.equals(goal)) {
                found = true;
            }
            cost++;
            for (Position child : getAdjacencyPositions(current.getPosition(), plateau)) {  //@TODO test if not outside of grid
                double tmp_g_score = current.getG_score() + cost;
                double tmp_f_score = tmp_g_score + child.getDistance(goal);
                Node childNode = new Node(child, current, goal);
                if (explored.contains(child) && tmp_f_score >= childNode.getF_score()) {
                    continue;
                } else if (!queue.contains(childNode) || (tmp_f_score < childNode.getF_score())) {  //@TODO change if condition, not sure if this is changing something
                    if (queue.contains(childNode)) {
                        queue.remove(childNode);
                    }
                    queue.add(childNode);
                }
            }
        }
        Queue<Position> way = new ArrayDeque<>();
        while (current.getParent() != null) {
            ((ArrayDeque<Position>) way).addFirst(current.getParent().getPosition());
            current = current.getParent();
        }
        return way;
    }

    public static List<Position> getAdjacencyPositions(Position position, Plateau plateau){
        List<Position> availablePositions = new ArrayList<>();
        for (Agent.Move move : Agent.Move.values()) {
            Position tmp = Agent.positionsAround(position, move);
            if (plateau.isPositionAvailable(tmp)) {
                availablePositions.add(tmp);
            }
        }
        return availablePositions;
    }

    public static List<Node> getNodes(Plateau plateau) {
        HashSet<Position> added = new HashSet<>();
        List<Node> nodes = new ArrayList<>();
        boolean[][] grille = plateau.getGrille();
        for (int x = 0; x < plateau.getNbCols();x++) {
            for (int y = 0; y < plateau.getNbLignes(); y++) {
                if (!grille[x][y]) {
                    continue;
                }
                Position position = new Position(x, y);
                added.add(position);
                List<Position> adjagencyPositions = getAdjacencyPositions(position, plateau);
                Node node = new Node(position);

            }
        }
        return null;
    }

    public int getCout() {
        return cout;
    }

    public List<Position> getWay() {
        return way;
    }

}
