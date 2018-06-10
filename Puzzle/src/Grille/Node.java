package Grille;

public class Node {

    private Position position;
    private double g_score = 1000;
    private double h_score = 1000;
    private Node parent;
    private final int movement_cost = 1;


    public Node(Position position, Node parent, Position goal) {
        this.position = position;
        this.parent = parent;
        this.g_score = parent.getG_score() + movement_cost;
        this.h_score = goal.getDistance(position);
    }
    public Node(Position position) {
        this.position = position;
        this.g_score = 0;
        this.parent = null;
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
        return this.g_score + this.h_score;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        Node n = (Node)obj;
        return n.getPosition().equals(this.getPosition());
    }

    public boolean equals(Position position) {
        return this.getPosition().equals(position);
    }

}
