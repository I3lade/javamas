/* 
 * The MIT License
 *
 * Copyright 2018 Guillaume Monet.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.eloane.javamas.agents.test.sensors;

import fr.eloane.javamas.kernel.Agent;

/**
 *
 * @author Guillaume Monet
 */
public class Sensor extends Agent {

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
    protected void handleSensor(fr.eloane.javamas.kernel.Sensor sensor) {
	this.sensor_value = (Integer) sensor.getValue();
    }

    public static void main(String[] args) throws InterruptedException {
	fr.eloane.javamas.kernel.Sensor<Integer> sensor = new fr.eloane.javamas.kernel.Sensor<>(1);
	Sensor sens = new Sensor();
	sens.addSensor(sensor);
	sens.setDelay(500);
	sens.start();
	Thread.sleep(5000);
	sensor.setValue(new Integer(110));
	Thread.sleep(5000);
	sensor.setValue(new Integer(210));
        sens.stop();
    }
}
