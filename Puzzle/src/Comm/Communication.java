package Comm;

import Grille.Agent;

import java.util.*;

public class Communication {

    private static Communication INSTANCE = null;

    private HashMap<Agent, LinkedList<Message>> messages;

    public static Communication getInstance(){
        return INSTANCE == null ? INSTANCE = new Communication(): INSTANCE;
    }

    private Communication() {
        messages = new HashMap<>();
    }

    public void setCommunication(ArrayList<Agent> listAgents){
        for (Agent agent: listAgents) {
            messages.put(agent,new LinkedList<>());
        }
    }

    public Message readMessage(Agent agent){
        return messages.get(agent).pollFirst();
    }
}
