import java.util.*;

public class Communication {

    private static Communication INSTANCE = new Communication();

    private HashMap<Agent, LinkedList<Message>> messages;

    public static Communication getInstance(){
        return INSTANCE;
    }

    public Communication() {
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
