package javamas.agents.test.sensors;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentSensor;

/**
 *
 * @author Guillaume Monet
 */
public class Sensor extends AbstractAgent {

    
    int sensor_value = 0;
    @Override
    protected void activate() {
	
    }

    @Override
    protected void live() {
	while(this.nextStep()){
	    System.out.println(sensor_value);
	}
    }

    @Override
    public void end() {
    }

    @Override
    protected void handleSensor(AgentSensor sensor) {
	this.sensor_value = (Integer) sensor.getValue();
    }
    
    
    
    public static void main(String[] args) throws InterruptedException{
	AgentSensor<Integer> sensor = new AgentSensor<Integer>(1);
	Sensor sens = new Sensor();
	sens.addSensor(sensor);
	sens.setDelay(500);
	sens.start();
	Thread.sleep(5000);
	sensor.setValue(new Integer(110));
	Thread.sleep(5000);
	sensor.setValue(new Integer(210));
	
    }
}
