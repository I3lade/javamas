package javamas.agents.test.messages;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentNode;
import javamas.kernel.messages.Message;

/*
 * Convoyeur.java
 *
 * Created on April 29, 2003, 2:35 PM
 */
/**
 *
 * @author monet
 * @version
 */
public class Receiver_test extends AbstractAgent {

    
    

    /**
     *
     */
    @Override
    public void activate() {
	this.joinGroupe("Convoyeur");

    }

    /**
     *
     */
    @Override
    public void live() {
	Message<String> mes = (Message<String>) this.waitNextMessage();
	System.out.println("Content :" + mes.getContent());
	System.out.println("Sender :" + mes.getSender());
	Message<String> m = new Message<String>("DTC");
	m.setContent("DTC DEEP");
	this.sendMessage(mes.getSender(), m);

    }

    /**
     *
     */
    @Override
    public void end() {
    }
    
    public static void main(String[] args){
	AgentNode.getHandle().setLocal(false);
	(new Receiver_test()).start();
    }
}
