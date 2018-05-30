public class Message {
    
    private Agent emitter;
    private Agent recipient;
    private String type;
    private String action;
    private Position position;

    public Message(Agent emitter, Agent recipient, String type, String action, Position position) {
        this.emitter = emitter;
        this.recipient = recipient;
        this.type = type;
        this.action = action;
        this.position = position;
    }

    public Agent getEmitter() {
        return emitter;
    }

    public void setEmitter(Agent emitter) {
        this.emitter = emitter;
    }

    public Agent getRecipient() {
        return recipient;
    }

    public void setRecipient(Agent recipient) {
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
