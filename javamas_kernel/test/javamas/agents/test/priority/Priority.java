package javamas.agents.test.priority;

import javamas.kernel.AbstractAgent;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class Priority extends AbstractAgent{

    @Override
    public void activate() {
	this.joinGroupe("PRIORITY");
    }

    @Override
    public void live() {
	while(this.nextStep()){
	    System.out.println("WAITING");
	}
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
    }

    @Override
    public void end() {
	
    }
    
    public static void main(String[] args){
	Priority p = new Priority();
	p.setDelay(1000);
	p.start();

	
	AbstractAgent a = new AbstractAgent() {

	    @Override
	    public void activate() {
		
	    }

	    @Override
	    public void live() {
		
	    }

	    @Override
	    public void end() {
		
	    }
	};
	a.joinGroupe("PRIORITY");
	Message<String> m = new Message<String>();
	m.setContent("LOW PRIORITY");
	m.setPriority(Message.LOW_PRIORITY);
	a.broadcastMessage("PRIORITY", m);
	System.out.println("Send Low priority");
	
	m.setContent("HIGH PRIORITY");
	m.setPriority(Message.HIGH_PRIORITY);
	a.broadcastMessage("PRIORITY", m);
	System.out.println("Send HI priority");
	
	m.setContent("NORMAL PRIORITY");
	m.setPriority(Message.NORMAL_PRIORITY);
	a.broadcastMessage("PRIORITY", m);
	System.out.println("Send Normal priority");
	
	m.setContent("EXTREM PRIORITY");
	m.setPriority(Message.EXTREM_PRIORITY);
	a.broadcastMessage("PRIORITY", m);
	System.out.println("Send Extrem priority");
	
	m.setContent("NORMAL PRIORITY");
	m.setPriority(Message.NORMAL_PRIORITY);
	a.broadcastMessage("PRIORITY", m);
	System.out.println("Send Normal priority");
	p.stop();
	
    }
    
    
}
