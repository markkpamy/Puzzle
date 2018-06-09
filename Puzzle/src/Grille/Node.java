package Grille;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Position position;
    private double g_score = 1000;
    private double h_score = 1000;
    private double f_score = 1000;
    private List<Node> adjacencies;
    private Node parent;
    private final int movement_cost = 1;

    public Node(Position position) {
        this.position = position;
        adjacencies = new ArrayList<>();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getG_score() {
        return g_score;
    }

    public void setG_score(double g_score) {
        this.g_score = g_score;
    }

    public double getH_score() {
        return h_score;
    }

    public void setH_score(double h_score) {
        this.h_score = h_score;
    }

    public double getF_score() {
        return f_score;
    }

    public void setF_score(double f_score) {
        this.f_score = f_score;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addAdjacencyNode(Node node) {
        this.adjacencies.add(node);
    }
}
