package Comm;

import Grille.Agent;
import Grille.Coordonnees;

public class Message {

    private Agent emitter;
    private Agent recipient;
    private String type;
    private String action;
    private Coordonnees coordonnees;

    public Message(Agent emitter, Agent recipient, String type, String action, Coordonnees coordonnees) {
        this.emitter = emitter;
        this.recipient = recipient;
        this.type = type;
        this.action = action;
        this.coordonnees = coordonnees;
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

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }
}
