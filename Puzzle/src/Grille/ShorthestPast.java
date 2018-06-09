package Grille;

import javafx.geometry.Pos;

import java.util.*;

public class ShorthestPast {

    private int cout;
    private List<Position> way;
    private Agent agent;
    private boolean[][] grille;

    public static List<Position> aStar(Agent agent, Plateau plateau) {
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
            for (Position child : getAdjacencyPositions(current.getPosition(), plateau)) {
                double tmp_g_score = current.getG_score() + cost;
                double tmp_f_score = tmp_g_score + child.getDistance(goal);
                Node childNode = new Node(child);
                if (explored.contains(child) && tmp_f_score >= childNode.getH_score()) {
                    continue;
                } else if (!queue.contains(childNode) || (tmp_f_score < childNode.getF_score())) {
                    childNode.setParent(current);
                    childNode.setG_score(tmp_g_score);
                    childNode.setF_score(tmp_f_score);

                    if (queue.contains(childNode)) {
                        queue.remove(childNode);
                    }

                    queue.add(childNode);
                }
            }
        }
        List<Position> way = new ArrayList<>();
        while (current.getParent() != null) {
            way.add(current.getParent().getPosition());
            current = current.getParent();
        }
        Collections.reverse(way);
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

    public int getCout() {
        return cout;
    }

    public List<Position> getWay() {
        return way;
    }

}
