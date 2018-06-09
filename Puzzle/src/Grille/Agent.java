/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import Comm.Communication;
import Comm.Message;
import javafx.geometry.Pos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;

/**
 * @author markk
 */
public class Agent implements Runnable {

    public enum Color {
        CYAN, GREEN, RED, YELLOW, BLUE, PURPLE, BLACK, ORANGE
    }

    public enum Move {
        RIGHT, LEFT, UP, DOWN
    }

    private final int idAgent;
    private final String nameAgent;
    private Color color;
    private Case currentCase;
    private Case goalCase;
    private Plateau plateau;
    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private Move lastPosition;

    public Agent(int idAgent, String nameAgent, Case currentCase, Color color) {
        this(idAgent, nameAgent, currentCase, new Case(new Position(currentCase.getPosition().getY(), currentCase.getPosition().getX())), color);
    }

    public Agent(int idAgent, String nameAgent, Case currentCase, Case goalCase, Color color) {
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.currentCase = currentCase;
        this.goalCase = goalCase;
        this.color = color;
    }

    public Agent() {
        this.color = Color.RED;
        idAgent = 0;
        nameAgent = "Agent";
        this.currentCase = new Case(new Position(9, 0));
    }

    @Override
    public void run() {
        while (!this.plateau.isFinished()) {
            //System.out.println("dans le while");
            if (!this.isGoalReached()) {
                setUp();
            }
            receiveAndMove();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Communication.getInstance().displayMessages();
//            if (this.getColor() == Color.GREEN) {
//                Communication.getInstance().displayMessages();
//            }
        }
    }

    private void receiveAndMove() {
        Message message = null;
        if ((message = Communication.getInstance().readMessage(this)) != null) {
            switch (message.getType()) {
                case "request":
                    switch (message.getAction()) {
                        case "move":
                            setUp();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }

        }
    }
    private synchronized void setUp() {
        Map<Move, Position> nextMoves = isGoalReached()? moveEvenIfFinished(): chooseNextMove();
        this.plateau.effaceTracePiece(this);
        boolean succeed = false;
        for (Map.Entry<Move, Position> entry : nextMoves.entrySet()) {
            if (move(this.plateau, entry.getKey())){
                if (this.lastPosition == entry.getKey()) {
                    break;
                }
                switch (entry.getKey()) {
                    case UP:this.lastPosition = Move.RIGHT;
                    break;
                    case DOWN: this.lastPosition = Move.UP;
                    break;
                    case LEFT: this.lastPosition = Move.RIGHT;
                    break;
                    case RIGHT: this.lastPosition = Move.LEFT;
                    break;
                    default: this.lastPosition = null;
                }
                break;
            }
        }
        this.plateau.updatePlateau(this);
    }

    private void sendMessage(Plateau plateau, Position position) {
        Agent agent = plateau.findAgent(position);
        if (agent!= null) {
            Message message = new Message(this, agent, "request", "move", goalCase.getPosition());
            Communication.getInstance().writeMessage(agent, message);
            messageArrayList.add(message);
        }
    }

    public boolean move(Plateau plateau, Move move) {
        if (verifMove(plateau, move)) {
            //System.out.println(this);
            this.getCurrentCase().setPosition(positionByMove(move));
            return true;
        }
        return false;
    }


    public boolean verifMove(Plateau plateau, Move move) {
        boolean result = true;
        Position position = positionByMove(move);
        switch (move) {
            case RIGHT:
                if ((position.getY() > plateau.getNbCols() - 1)) {
                    return false;
                }
                break;
            case LEFT:
                if (position.getY() < 0) {
                    return  false;
                }
                break;
            case UP:
                if (position.getX() < 0) {
                    return false;
                }
                break;
            case DOWN:
                if (position.getX() > plateau.getNbLignes() - 1) {
                    return false;
                }
                break;
        }
       // System.out.println(position);
        if (plateau.getGrille()[position.getX()][position.getY()]){
//            System.out.println("bloque");
            sendMessage(plateau, position);
            return false;
        }
        return true;
    }

    public Map<Move,Position> moveEvenIfFinished(){
        Move move = Move.RIGHT;
        Map<Move, Position> positions = new HashMap<>();
        Map<Move, Position> positionsResult = new HashMap<>();
        List<Integer> fourthFirstNumber = new ArrayList<>();
        fourthFirstNumber.add(0);
        fourthFirstNumber.add(1);
        fourthFirstNumber.add(2);
        fourthFirstNumber.add(3);
        Collections.shuffle(fourthFirstNumber);
//        System.out.println(fourthFirstNumber);
        List<Map<Move,Position>> positionMap = new ArrayList<>();
        for(int i = 0; i<4; i++) { positionMap.add(i,new HashMap<>());}
        positionMap.get(fourthFirstNumber.get(0)).put(Move.RIGHT, positionByMove(move));
        positionMap.get(fourthFirstNumber.get(1)).put(Move.LEFT, positionByMove(Move.LEFT));
        positionMap.get(fourthFirstNumber.get(2)).put(Move.UP, positionByMove(Move.UP));
        positionMap.get(fourthFirstNumber.get(3)).put(Move.DOWN, positionByMove(Move.DOWN));
        for (Map<Move,Position> positionTemp : positionMap) {
            positions.put(positionTemp.keySet().iterator().next(),positionTemp.values().iterator().next());
        }
        positions.forEach((key, value) -> {
            if (verifMove(plateau, key)) {
                positionsResult.put(key,value);
            }
        });
        return positionsResult;
    }
    /**
     * See which position is the closest to the goal
     */
    public Map<Move, Position> chooseNextMove() {
        // right
        Move move = Move.RIGHT;
        Map<Move, Position> positions = new HashMap<>();
        Map<Move, Position> positionsResult = new HashMap<>();
        List<Integer> fourthFirstNumber = new ArrayList<>();
        fourthFirstNumber.add(0);
        fourthFirstNumber.add(1);
        fourthFirstNumber.add(2);
        fourthFirstNumber.add(3);
        Collections.shuffle(fourthFirstNumber);
        List<Map<Move,Position>> positionMap = new ArrayList<>();
        for(int i = 0; i<4; i++) { positionMap.add(i,new HashMap<>());}
        positionMap.get(fourthFirstNumber.get(0)).put(Move.RIGHT, positionByMove(move));
        positionMap.get(fourthFirstNumber.get(1)).put(Move.LEFT, positionByMove(Move.LEFT));
        positionMap.get(fourthFirstNumber.get(2)).put(Move.UP, positionByMove(Move.UP));
        positionMap.get(fourthFirstNumber.get(3)).put(Move.DOWN, positionByMove(Move.DOWN));
        for (Map<Move,Position> positionTemp : positionMap) {
            positions.put(positionTemp.keySet().iterator().next(),positionTemp.values().iterator().next());
        }
        Map<Move, Position> result2 = new LinkedHashMap<>();
        positions.entrySet().stream()
                .sorted(new Comparator<Map.Entry<Move, Position>>() {
                    @Override
                    public int compare(Map.Entry<Move, Position> o1, Map.Entry<Move, Position> o2) {
                        Integer a = (int)goalCase.getPosition().getDistance(o1.getValue());
                        Integer b = (int)goalCase.getPosition().getDistance(o2.getValue());
                        return a.compareTo(b);
                    }
                })
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        
        return result2;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public String getNameAgent() {
        return nameAgent;
    }

    public Case getCurrentCase() {
        return currentCase;
    }

    public void setCurrentCase(Case currentCase) {
        this.currentCase = currentCase;
    }

    public List<Position> getShortestPath() {
//getshortestpath à completer
        return null;
    }

    public Case getGoalCase() {
        return goalCase;
    }

    public void setGoalCase(Case goalCase) {
        this.goalCase = goalCase;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Position positionByMove(Move move) {
        Position position = null;
        switch (move) {
            case UP:
                position = new Position(currentCase.getPosition().getX() -1,currentCase.getPosition().getY());
                break;
            case DOWN:
                position = new Position(currentCase.getPosition().getX() +1,currentCase.getPosition().getY());
                break;
            case LEFT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() - 1);
                break;
            case RIGHT:
                position = new Position(currentCase.getPosition().getX(),currentCase.getPosition().getY() + 1);
                break;
        }
        return position;
    }

    public boolean isGoalReached() {
        return currentCase.getPosition().equals(goalCase.getPosition());
    }

    @Override
    public String toString() {
        return "Agent:"+this.color + " position:" + this.currentCase.getPosition() + " destination:" + this.goalCase.getPosition();
    }
}
