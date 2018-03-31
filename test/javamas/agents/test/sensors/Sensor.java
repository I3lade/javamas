/**
 * JavaMas : Java Multi-Agents System Copyright (C) 2013 Guillaume Monet
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package javamas.agents.test.sensors;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentSensor;

/**
 *
 * @author Guillaume Monet
 */
public class Sensor extends AbstractAgent {

    private int sensor_value = 0;

    @Override
    protected void activate() {
    }

    @Override
    protected void live() {
	while (this.nextStep()) {
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

    public static void main(String[] args) throws InterruptedException {
	AgentSensor<Integer> sensor = new AgentSensor<>(1);
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
