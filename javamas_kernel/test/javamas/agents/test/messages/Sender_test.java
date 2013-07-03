package javamas.agents.test.messages;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentNode;
import javamas.kernel.messages.ACLMessage;
import javamas.kernel.messages.Message;

/**
 *
 * @author monet
 * @version
 */
public class Sender_test extends AbstractAgent {


    /**
     *
     */
    @Override
    public void activate() {
	this.joinGroupe("Fourmis");
    }

    /**
     *
     */
    @Override
    public void live() {
	int cpt = 0;
	this.broadcastMessage("Convoyeur", new Message<String>("WHERE ARE YOU"));
	Message<String> mes = this.waitNextMessage();
	System.out.println("Content :" + mes.getContent());
	System.out.println("Sender :" + mes.getSender());
    }

    /**
     *
     */
    @Override
    public void end() {
    }
    
    public static void main(String[] args){
	AgentNode.getHandle().setLocal(false);
	(new Sender_test()).start();
    }
}
