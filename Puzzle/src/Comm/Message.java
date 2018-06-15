package Comm;

import Grille.Agent;
import Grille.Position;

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

    public Message() {
        this.emitter = new Agent();
        this.recipient = new Agent();
        this.type = " ";
        this.action = " ";
        this.position = new Position(0,0);
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

    @Override
    public String toString() {
        return new StringBuilder().append("[")
                .append(getEmitter())
                .append(", ")
                .append(getRecipient())
                .append(", ")
                .append(getType())
                .append(", ")
                .append(getAction())
                .append(", ")
                .append(getPosition())
                .append("]")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        Message message = (Message) obj;
        if (message == null) {
            return false;
        } else {
            return this.emitter == message.getEmitter() &&
                    this.recipient == message.getRecipient() &&
                    this.action.equals(message.getAction()) &&
                    this.type.equals(message.getType());
        }
    }
}
