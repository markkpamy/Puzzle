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
        if (this.messages != null && agent != null && this.messages.get(agent.getIdAgent()) !=null) {
            if (this.messages.get(agent.getIdAgent()).contains(message)) {
                return false;
            }
            this.messages.get(agent.getIdAgent()).addLast(message);
        } else return false;


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
        try {
            if (mainAgent!= null && futurReceiver !=null) {
                LinkedList<Message> messageArrayList = this.messages.get(mainAgent.getIdAgent());
                if (messageArrayList.size()>0 && !messageArrayList.isEmpty() && messageArrayList != null ) {
                    for (Message message : messageArrayList) {
                        if ((message !=null) && (!message.isEmpty()) && (message.getEmitter().getIdAgent() == futurReceiver.getIdAgent()) && message.getType().equals("request") && message.getAction().equals("move")) {
                            return true;
                        }
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            throw new ConcurrentModificationException();
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        return false;

    }

    public void resetMessagesOfAgent(Agent agent) {
        this.messages.get(agent.getIdAgent()).clear();
    }
}
