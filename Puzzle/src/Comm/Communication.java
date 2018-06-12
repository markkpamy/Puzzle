package Comm;

import Grille.Agent;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Communication {

    private static Communication INSTANCE = null;

    private HashMap<Integer, LinkedList<Message>> messages;

    public static Communication getInstance(){
        return INSTANCE == null ? INSTANCE = new Communication(): INSTANCE;
    }

    private Communication() {
        messages = new HashMap<>();
    }

    public void setCommunication(ArrayList<Agent> listAgents){
        for (Agent agent: listAgents) {
            messages.put(agent.getIdAgent(),new LinkedList<>());
        }
    }

    public Message readMessage(Agent agent){
        return this.messages.get(agent.getIdAgent()).pollFirst();
    }

    public boolean writeMessage(Agent agent, Message message) {
        if (this.messages.get(agent.getIdAgent()).contains(message)) {
            return false;
        }
        this.messages.get(agent.getIdAgent()).addLast(message);
        return true;
    }

    public void displayMessages(){
        this.messages.forEach((a,b) -> System.out.println(String.valueOf(a) + " " + b.toString()));
    }

    public void displayMessagesofAgent(Agent agent){
        System.out.println(this.messages.get(agent.getIdAgent()));
    }

    public void clearMessageOfAllAgent() {
        this.messages.forEach((i,l) -> l.clear());
    }

    public boolean checkMessageReceivedByPosition(Agent mainAgent,Agent futurReceiver) {
        boolean check = false;
        Iterator iterator = this.messages.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Integer, LinkedList<Message>> it = (Map.Entry<Integer, LinkedList<Message>>) iterator.next();

            do {

                if (mainAgent.getIdAgent() == it.getKey()) {
                    for (Message message : it.getValue()) {
                        if (message.getEmitter().getIdAgent() == futurReceiver.getIdAgent()) {
//                            System.out.println("vrai");
                            return true;
                        }
                    }
                }

                it = (Map.Entry<Integer, LinkedList<Message>>) iterator.next();
            } while (iterator.hasNext());
        }
        return false;
    }

    public void resetMessagesOfAgent(Agent agent) {
        this.messages.get(agent.getIdAgent()).clear();
    }
}
