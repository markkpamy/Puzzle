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
    private ArrayList<Message> messageArrayList = new ArrayList<>();
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
//        for (Case[] c1:plateau.getPanCases()) {
//            for (Case c2: c1) {
//
//            }
//        }
    }

    public Agent() {
        this.color = Color.RED;
        idAgent = 0;
        nameAgent = "Agent";
        this.currentCase = new Case(new Position(9, 0));
    }

    @Override
    public void run() {
//        plateau = new Plateau(5,5);
//        Agent cptAmerica2 = new Agent(12, "cptAmerica", new Case(new Position(0, 0)), Agent.Color.ORANGE);
//        Map<Integer, Agent> map = new HashMap<>();
//        map.put(cptAmerica2.idAgent,cptAmerica2);
//        plateau.setAgentMap(map);
//        System.out.println(isInLimits(plateau,Move.LEFT,new Position(0,0)));
        while (!this.plateau.isFinished()) {
//            System.out.println("agent: " + this.getIdAgent());
            //System.out.println("dans le while");
            try {
                if (!this.isGoalReached()) {
                    setUp();
                }
                receiveAndMove();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Agent: " + getIdAgent() + e.getMessage());
                e.printStackTrace();
            }
//            Communication.getInstance().displayMessages();
//            if (this.getColor() == Color.GREEN) {
//                Communication.getInstance().displayMessages();
//            }
//            if (isGoalReached()) System.out.println("agent" + idAgent + "arrivé");
        }
        System.out.println("Agent: " + idAgent + "finished");
    }

    private void receiveAndMove() throws InterruptedException {
//        System.out.println(idAgent + "receiveAndMove enter");
        Message message = null;
//        System.out.println("dans receive en move de " + idAgent);
            if ((message = Communication.getInstance().readMessage(this)) != null) {
                processMessage(message);

            }

//        System.out.println(idAgent + " receiveAndMove sortie");
    }

    private void processMessage(Message message) throws InterruptedException {
        switch (message.getType()) {
            case "request":
                switch (message.getAction()) {
                    case "move":
                        System.out.println("reçu par agent:" + idAgent);
                        requestFromAgent = message.getEmitter();
                        letPlace();
                        Communication.getInstance().writeMessage(message.getEmitter(), new Message(this, message.getEmitter(), "response", "yes", this.goalCase.getPosition()));
                        Thread.sleep(600);
                        break;
                    default:
                        break;
                }
                break;
            case "response":
                switch (message.getAction()) {
                    case "yes":
                        System.out.println(idAgent + " ok je peux continuer");
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
        int count = 0;
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
//            System.out.println("c'est sent :" + sent);

            Agent testAgent = plateau.findAgent(position);
//            System.out.println(testAgent);
            if ((testAgent != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, testAgent))) {
                return;
            }
            if (sent) {
                waitForAnswer(testAgent);
            }

        }
    }

    public void letPlace() throws InterruptedException {

//        System.out.println(idAgent + " letPlace entrte");
        int count = 0;
        int nbOfTry = 0;
        Message message = new Message();
        Map.Entry<Move, Position> movePositionEntry = null;
        Agent agentTemp = null;
            Map<Move, Position> nextMoves = moveEvenIfFinished();
        Iterator ite = nextMoves.entrySet().iterator();
//            do {
        if (ite == null || !ite.hasNext()) {
            nextMoves = bestCaseAround();
            ite = nextMoves.entrySet().iterator();

        }
                movePositionEntry = (Map.Entry<Move, Position>) ite.next();
                agentTemp = plateau.findAgent(movePositionEntry.getValue());
//                if (agentTemp == null) {
//                    break;
//                }
//            } while (agentTemp.getIdAgent() == requestFromAgent.getIdAgent());

//            if (agentTemp != null) {
//                System.out.println("agent: "+ agentTemp.requestFromAgent + " request: " + requestFromAgent.getIdAgent());
//            }
//        System.out.println(idAgent +" move:" + movePositionEntry.getKey() + "destination:" + plateau.findAgent(movePositionEntry.getValue()) + " by " + idAgent + " from : " + requestFromAgent.getIdAgent());
//        System.out.println(this.idAgent + " " + "position:" + this.currentCase.getPosition() + " next move" + nextMoves);


        if (move(this.plateau, movePositionEntry.getKey())) {
            System.out.println(idAgent + " a fait " + movePositionEntry.getKey() + " de " + movePositionEntry.getValue());
            setLastPosition (movePositionEntry.getKey());
        } else {


            Agent test= plateau.findAgent(movePositionEntry.getValue());
            if (test!=null && test.getIdAgent() == this.idAgent) {
                return;
            }
            System.out.println(idAgent + "send message to " + plateau.findAgent(movePositionEntry.getValue()));
            boolean sent = sendMessage(plateau, movePositionEntry.getValue());
//            System.out.println("c'est sent :" + sent);
            if ((test != null) && (Communication.getInstance().checkMessageReceivedByPosition(this, test))) {
                return;
            }
            if (sent) {
                waitForAnswer(test);
            }
        }



//        System.out.println(idAgent + " letPlace sortie");
    }

    public void waitForAnswer(Agent receiver) throws InterruptedException {
        if (receiver != null) {
            Message message = null;
            do {

                System.out.println(idAgent + " wait... for " + receiver.getIdAgent());
                Thread.sleep(100);
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
    private Map<Move,Position> casesAroundChecked() {

        Map<Move,Position> casesAroundChecked = new HashMap<>();
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(Move.RIGHT);
        moves.add(Move.LEFT);
        moves.add(Move.UP);
        moves.add(Move.DOWN);
        for (Move move : moves) {
            Position position = getPositionByMove(move);
            if (!(position.getY() > plateau.getNbCols() - 1) && !(position.getY() < 0) && !(position.getX() < 0) && !(position.getX() > plateau.getNbLignes() - 1)) {
                casesAroundChecked.put(move,position);
            }

        }
        return casesAroundChecked;
    }


    private Map<Move,Position> casesAround() {
        Map<Move, Position> positions = new HashMap<>();
        List<Integer> fourthFirstNumber = new ArrayList<>();
        fourthFirstNumber.add(0);
        fourthFirstNumber.add(1);
        fourthFirstNumber.add(2);
        fourthFirstNumber.add(3);
        Collections.shuffle(fourthFirstNumber);
        List<Map<Move,Position>> positionMap = new ArrayList<>();
        for(int i = 0; i<4; i++) { positionMap.add(i,new HashMap<>());}
        positionMap.get(fourthFirstNumber.get(0)).put(Move.RIGHT, getPositionByMove(Move.RIGHT));
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
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        return result2;
    }

    private boolean sendMessage(Plateau plateau, Position position) {
        Agent agent = plateau.findAgent(position);
        if (agent!= null && !(agent.getIdAgent() == this.idAgent)) {
//            System.out.println(idAgent + " send message to:" + agent.getIdAgent());
            Message message = new Message(this, agent, "request", "move", goalCase.getPosition());
            return Communication.getInstance().writeMessage(agent, message);
        } else return false;
    }

    public synchronized boolean move(Plateau plateau, Move move) {
        if (verifMove(plateau, move)) {
//            System.out.println(idAgent + "j'ai bougé");
            plateau.effaceTracePiece(this);
            this.getCurrentCase().setPosition(getPositionByMove(move));
//            System.out.println(idAgent + " position changé je suis à " + this.currentCase.getPosition());
            plateau.updatePlateau(this);
            return true;
        }
        return false;
    }


    public boolean verifMove(Plateau plateau, Move move) {
        boolean result = true;
        Position position = getPositionByMove(move);
        if (!isInLimits(plateau, move, position)) {
            return false;
        }
       // System.out.println(position);
        if (plateau.getGrille()[position.getX()][position.getY()]){
//            System.out.println(idAgent + "bloque car x:" + position.getX() + " y: " + position.getY() + "prise, request from" + requestFromAgent.getIdAgent());
//            sendMessage(plateau, position);
            return false;
        }
        return true;
    }

    public boolean isInLimits(Plateau plateau, Move move, Position position) {
        boolean result = true;
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
//        System.out.println(idAgent + " entré dans moveEventIfFinished and goalReach: " + isGoalReached());
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
        });
//        System.out.println(idAgent + "moves positions possibles: " + positionsResult);
//        Thread.sleep(1500);
//        System.out.println(idAgent +"sorti de venifENded");
        return positionsResult;
    }
    /**
     * See which position is the closest to the goal
     */
    public Map<Move, Position> chooseNextMove() {
        return bestWay();
    }

    public Map<Move, Position> bestWay() {
        //        System.out.println("rentré dans chooseNextMove at position :" + this.currentCase.getPosition().toString());
        ShorthestPast shorthestPast = new ShorthestPast(this, this.plateau);
        way = shorthestPast.aStar();
//        if (way == null) {
//            System.out.println("erreur way is empty ");
//            return null;
//        } else {
//            System.out.println("Agent: " + this.idAgent + " way:" + way);
//        }
        Position nextMove = way.poll();
        nextMove = way.poll();
//        System.out.println("nextMove: " + getMoveByPositon(nextMove) + "nextPosition" + nextMove);
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
                    }
                });
//        System.out.println(result2);
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
