/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import Comm.Communication;
import Comm.Message;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
    private Move lastPosition;
    private Queue<Position> way;
    private Agent requestFromAgent;

    public Agent(int idAgent, String nameAgent, Case currentCase, Color color) {
        this(idAgent, nameAgent, currentCase, new Case(new Position(currentCase.getPosition().getY(), currentCase.getPosition().getX())), color);
    }

    public Agent(int idAgent, String nameAgent, Case currentCase, Case goalCase, Color color) {
        this.idAgent = idAgent;
        this.nameAgent = nameAgent;
        this.currentCase = currentCase;
        this.goalCase = goalCase;
        this.color = color;
        this.requestFromAgent = new Agent();
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
            try {
                if (!this.isGoalReached()) {
                    setUp();
                }
                receiveAndMove();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Agent: " + getIdAgent() + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Agent: " + idAgent + " finished");
    }

    private void receiveAndMove() throws InterruptedException {
        Message message = null;
            if ((message = Communication.getInstance().readMessage(this)) != null) {
                processMessage(message);
            }
    }

    private void processMessage(Message message) throws InterruptedException {
        switch (message.getType()) {
            case "request":
                switch (message.getAction()) {
                    case "move":
                        requestFromAgent = message.getEmitter();
                        letPlace();
                        Communication.getInstance().writeMessage(message.getEmitter(), new Message(this, message.getEmitter(), "response", "yes", this.goalCase.getPosition()));
                        Thread.sleep(100);
                        break;
                    default:
                        break;
                }
                break;
            case "response":
                switch (message.getAction()) {
                    case "yes":
                        letPlace();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void setUp() throws InterruptedException {
        Message message = new Message();

        Map<Move, Position> nextMoves = isGoalReached() ? moveEvenIfFinished() : bestCaseAround();

        if (nextMoves == null || nextMoves.isEmpty()) {
            nextMoves = bestCaseAround();
        }

        Position position = nextMoves.values().iterator().next();
        Move move = nextMoves.keySet().iterator().next();
        if (position == null || move == null) {
            nextMoves = bestCaseAround();
            position = nextMoves.values().iterator().next();
            move = nextMoves.keySet().iterator().next();
        }

        if (move(this.plateau, move)) {
            setLastPosition(move);
        } else {
            boolean sent = sendMessage(plateau, position);
            Agent testAgent = plateau.findAgent(position);
            try {
                if ((testAgent != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, testAgent))) {
                    letPlace();
                }
            } catch (ConcurrentModificationException e) {
                if ((testAgent != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, testAgent))) {
                    letPlace();
                }
            } catch (NullPointerException e) {
            }
            if (sent) {
                waitForAnswer(testAgent);
            }

        }
    }

    public void letPlace() throws InterruptedException {

        Message message = new Message();
        Map.Entry<Move, Position> movePositionEntry = null;
        Agent agentTemp = null;
            Map<Move, Position> nextMoves = moveEvenIfFinished();
        Iterator ite = nextMoves.entrySet().iterator();
        if (ite == null || !ite.hasNext()) {
            nextMoves = bestCaseAround();
            ite = nextMoves.entrySet().iterator();

        }
                movePositionEntry = (Map.Entry<Move, Position>) ite.next();
                agentTemp = plateau.findAgent(movePositionEntry.getValue());


        if (move(this.plateau, movePositionEntry.getKey())) {
            setLastPosition (movePositionEntry.getKey());
        } else {


            Agent test= plateau.findAgent(movePositionEntry.getValue());
            if (test!=null && test.getIdAgent() == this.idAgent) {
                return;
            }
            boolean sent = sendMessage(plateau, movePositionEntry.getValue());
            try {
                if ((test != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, test))) {
                    return;
                }
            } catch (ConcurrentModificationException e) {
                if ((test != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, test))) {
                    return;
                }
            } catch (NullPointerException e) {
            }
            if (sent) {
                waitForAnswer(test);
            }
        }
    }

    public void waitForAnswer(Agent receiver) throws InterruptedException {
        if (receiver != null) {
            Message message = null;
            do {
                Thread.sleep(10);
            } while ((message = Communication.getInstance().readMessage(this)) == null);

            processMessage(message);
        }
    }

    private void setLastPosition(Move move) {
        switch (move) {
            case UP:
                this.lastPosition = Move.RIGHT;
                break;
            case DOWN:
                this.lastPosition = Move.UP;
                break;
            case LEFT:
                this.lastPosition = Move.RIGHT;
                break;
            case RIGHT:
                this.lastPosition = Move.LEFT;
                break;
            default:
                this.lastPosition = null;
        }
    }

    private boolean sendMessage(Plateau plateau, Position position) {
        Agent agent = plateau.findAgent(position);
        if (agent!= null && !(agent.getIdAgent() == this.idAgent)) {
            Message message = new Message(this, agent, "request", "move", goalCase.getPosition());
            return Communication.getInstance().writeMessage(agent, message);
        } else return false;
    }

    public synchronized boolean move(Plateau plateau, Move move) {
        if (verifMove(plateau, move)) {
            plateau.effaceTracePiece(this);
            this.getCurrentCase().setPosition(getPositionByMove(move));
            plateau.updatePlateau(this);
            return true;
        }
        return false;
    }


    public boolean verifMove(Plateau plateau, Move move) {
        Position position = getPositionByMove(move);
        if (!isInLimits(plateau, move, position)) {
            return false;
        }
        if (plateau.getGrille()[position.getX()][position.getY()]){
            return false;
        }
        return true;
    }

    public boolean isInLimits(Plateau plateau, Move move, Position position) {
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

        return true;
    }

    public Map<Move,Position> moveEvenIfFinished() throws InterruptedException {
        Move move = Move.RIGHT;
        Map<Move, Position> positions = new HashMap<>();
        Map<Move, Position> positionsResult = new HashMap<>();
        Map<Move, Position> testMovePosition = new HashMap<>();
        List<Integer> fourthFirstNumber = new ArrayList<>();
        fourthFirstNumber.add(0);
        fourthFirstNumber.add(1);
        fourthFirstNumber.add(2);
        fourthFirstNumber.add(3);
        Collections.shuffle(fourthFirstNumber);
        List<Map<Move,Position>> positionMap = new ArrayList<>();
        for(int i = 0; i<4; i++) { positionMap.add(i,new HashMap<>());}
        positionMap.get(fourthFirstNumber.get(0)).put(Move.RIGHT, getPositionByMove(move));
        positionMap.get(fourthFirstNumber.get(1)).put(Move.LEFT, getPositionByMove(Move.LEFT));
        positionMap.get(fourthFirstNumber.get(2)).put(Move.UP, getPositionByMove(Move.UP));
        positionMap.get(fourthFirstNumber.get(3)).put(Move.DOWN, getPositionByMove(Move.DOWN));
        for (Map<Move,Position> positionTemp : positionMap) {
            positions.put(positionTemp.keySet().iterator().next(),positionTemp.values().iterator().next());
        }

        positions.forEach((key, value) -> {
            Agent agentTemp = plateau.findAgent(getPositionByMove(key));
            if (agentTemp != null && agentTemp.isGoalReached()) {
                if (isInLimits(plateau, key, getPositionByMove(key))) {
                    positionsResult.put(key, value);
                }
            }
        });
        positions.forEach((key, value) -> {
            Agent agentTemp = plateau.findAgent(getPositionByMove(key));
            if (agentTemp== null || (!agentTemp.isGoalReached() && agentTemp.getIdAgent() != requestFromAgent.getIdAgent())) {
                if (isInLimits(plateau, key, getPositionByMove(key))) {
                    positionsResult.put(key, value);
                }
            }
            if (agentTemp == null && isInLimits(plateau, key,getPositionByMove(key))) {
                testMovePosition.put(key,value);
            }
        });
        if (!testMovePosition.isEmpty()) {

             Object[] randomKeySet = testMovePosition.keySet().toArray();
             Object key = randomKeySet[new Random().nextInt(randomKeySet.length)];
            Position position = positionsResult.get((Move)key);
            positionsResult.clear();
            positionsResult.put((Move) key,position);
        }
        return positionsResult;
    }
    /**
     * See which position is the closest to the goal
     */
    public Map<Move, Position> chooseNextMove() {
        return bestWay();
    }

    public Map<Move, Position> bestWay() {
        ShorthestPast shorthestPast = new ShorthestPast(this, this.plateau);
        way = shorthestPast.aStar();
        Position nextMove = way.poll();
        nextMove = way.poll();
        Map<Move, Position> result = new HashMap<>();
        result.put(getMoveByPositon(nextMove), nextMove);
        return result;
    }

    public Map<Move, Position> bestCaseAround() {
        Move move = Move.RIGHT;
        Map<Move, Position> positions = new HashMap<>();
        List<Integer> fourthFirstNumber = new ArrayList<>();
        fourthFirstNumber.add(0);
        fourthFirstNumber.add(1);
        fourthFirstNumber.add(2);
        fourthFirstNumber.add(3);
        Collections.shuffle(fourthFirstNumber);
        List<Map<Move,Position>> positionMap = new ArrayList<>();
        for(int i = 0; i<4; i++) { positionMap.add(i,new HashMap<>());}
        positionMap.get(fourthFirstNumber.get(0)).put(Move.RIGHT, getPositionByMove(move));
        positionMap.get(fourthFirstNumber.get(1)).put(Move.LEFT, getPositionByMove(Move.LEFT));
        positionMap.get(fourthFirstNumber.get(2)).put(Move.UP, getPositionByMove(Move.UP));
        positionMap.get(fourthFirstNumber.get(3)).put(Move.DOWN, getPositionByMove(Move.DOWN));
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
                .forEachOrdered(x -> {
                    Agent agent = plateau.findAgent(x.getValue());
                    if (agent == null || agent.getIdAgent() != requestFromAgent.getIdAgent()) {
                        result2.put(x.getKey(), x.getValue());
                        return;
                    }
                    return;
                });
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

    public Position getPositionByMove(Move move) {
        return positionsAround(currentCase.getPosition(), move);
    }

    public Move getMoveByPositon(Position position) {
        if (position == null) {
            return null;
        }
        if (this.currentCase.getPosition().getX() == position.getX()) {
            if (this.currentCase.getPosition().getY() + 1 == position.getY()) {
                return Move.RIGHT;
            } else if (this.currentCase.getPosition().getY() - 1 == position.getY()) {
                return Move.LEFT;
            }
        } else if (this.currentCase.getPosition().getY() == position.getY()) {
            if (this.currentCase.getPosition().getX() - 1 == position.getX()) {
                return Move.UP;
            } else if (this.currentCase.getPosition().getX() + 1 == position.getX()) {
                return Move.DOWN;
            }
        }
        return null;
    }

    public Position positionsAround(Position position, Move move) {
        Position result = null;
        switch (move) {
            case UP:
                result = new Position(position.getX() -1, position.getY());
                break;
            case DOWN:
                result = new Position(position.getX() +1,position.getY());
                break;
            case LEFT:
                result = new Position(position.getX(),position.getY() - 1);
                break;
            case RIGHT:
                result = new Position(position.getX(),position.getY() + 1);
                break;
        }
        return result;
    }

    public boolean isGoalReached() {
        return currentCase.getPosition().equals(goalCase.getPosition());
    }

    @Override
    public String toString() {
        return "Agent:"+this.getIdAgent() + " position:" + this.currentCase.getPosition() + " destination:" + this.goalCase.getPosition();
    }
}
