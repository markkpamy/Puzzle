import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Communication {

    private HashMap<Integer, ArrayList<Message>> messages;

    public Communication() {
        this.messages = new HashMap<>();
    }

    public Communication(int nb){
        for (int i = 0; i <= nb; i++) {
            this.messages.put(i,new ArrayList<>());
        }
    }
}
