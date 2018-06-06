package Comm;

import Grille.Agent;

import java.util.*;

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

    public void writeMessage(Agent agent, Message message) {
        this.messages.get(agent.getIdAgent()).addLast(message);
    }

    public void displayMessages(){
        this.messages.forEach((a,b) -> System.out.println(a + " " + b));
    }

    public void displayMessagesofAgent(Agent agent){
        System.out.println(this.messages.get(agent.getIdAgent()));
    }
}
