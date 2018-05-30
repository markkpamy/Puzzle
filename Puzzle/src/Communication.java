import java.util.*;

public class Communication {

    private HashMap<Agent, LinkedList<Message>> messages;

    public Communication() {
        this.messages = new HashMap<>();
    }

    public Communication(ArrayList<Agent> listAgents){
        for (Agent agent: listAgents) {
            this.messages.put(agent,new LinkedList<>());
        }
    }

    public Message readMessage(Agent agent){
        return messages.get(agent).pollFirst();
    }
}
