package javamas.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javamas.kernel.messages.Message;
import javamas.kernel.utils.SynchronizedPriority;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AbstractAgent.java
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 * @param <T> Class for the agent's inner database
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr>
 * @version 1.4
 */
public abstract class AbstractAgent<T> extends Observable implements Serializable, Runnable, Observer {

    private int hashcode;
    private ArrayList<AgentSensor<?>> sensors = new ArrayList<>();
    private AgentDatabase<T> database = null;
    private AgentGUI gui = null;
    private final SynchronizedPriority<Message<?>> messages = new SynchronizedPriority<>();
    private final AgentAddress address;
    private final AgentScheduler scheduler;
    private String name = "";
    private boolean daemon = false;
    private static final long serialVersionUID = 20130619L;

    /**
     * Create new Agent
     */
    public AbstractAgent() {
	this.scheduler = new AgentScheduler();
	this.hashcode = this.hashCode();
	address = new AgentAddress(this.hashcode);
	//database = new AgentDatabase<T>(true);
	this.register();
    }

    /**
     * Create new Agent
     *
     * @param name set the current public name for the agent
     */
    public AbstractAgent(String name) {
	this();
	this.name = name;
    }

    /**
     * Create new Agent
     *
     * @param daemon set if the agent is a daemon
     */
    public AbstractAgent(boolean daemon) {
	this();
	this.daemon = daemon;
    }

    /**
     * Create new Agent
     *
     * @param name set the current public name for the agent
     * @param daemon set if the agent is a daemon
     */
    public AbstractAgent(String name, boolean daemon) {
	this();
	this.name = name;
	this.daemon = daemon;
    }

    /**
     * Register the Agent to the current AgentNode
     */
    private void register() {
	AgentNode.getHandle().register(this);
    }

    /**
     * Unregister the Agent from the current AgentNode
     */
    private void unregister() {
	AgentNode.getHandle().unregister(this);
    }

    /**
     * Life of the agent init initGUI activate live end kill private method
     * destroy all objects from the agents
     */
    @Override
    public final void run() {
	this.init();
	this.initGUI();
	this.activate();
	this.live();
	this.end();
	this.kill();
    }

    /**
     * Start the life cycle
     */
    public final void start() {
	Thread th = new Thread(this);
	th.setDaemon(daemon);
	th.start();
    }

    /**
     * Initialize of the Agent can be overridden
     */
    protected void init() {
    }

    /**
     * Initialize GUI for the agent
     */
    protected void initGUI() {
    }

    /**
     * Active the agent Initialize all things Agent life need
     */
    protected abstract void activate();

    /**
     * life of the agent
     */
    protected abstract void live();

    /**
     * end of the agent
     */
    public abstract void end();

    /**
     * Kill de agent
     */
    private void kill() {
	this.unregister();
	this.flushMessage();
	this.flushProbes();
	this.flushSensors();
	if (this.hasGUI()) {
	    this.gui.close();
	}
    }

    /**
     * Pause the current Agent's life cycle
     *
     * @see #nextStep()
     * @see #stop()
     * @see #resume()
     * @see #pause(long)
     */
    public final void pause() {
	scheduler.pause();
    }

    /**
     * Pause the current Agent's life cycle during time
     *
     * @see #nextStep()
     * @see #stop()
     * @see #resume()
     * @see #pause()
     * @param time
     */
    public final void pause(long time) {
	scheduler.pause(time);
    }

    /**
     * Resume the current Agent's life cycle
     *
     * @see #stop()
     * @see #nextStep()
     * @see #pause()
     * @see #pause(long)
     */
    public final void resume() {
	scheduler.resume();
    }

    /**
     * Stop the current Agent's life cycle
     *
     * @see #nextStep()
     * @see #pause()
     * @see #pause(long)
     * @see #resume()
     */
    public final void stop() {
	scheduler.stop();
    }

    /**
     * Set the current delay beetween each Agent's life cycle
     *
     * @see #nextStep()
     * @see #pause()
     * @see #pause(long)
     * @see #stop()
     * @see #resume()
     * @param delay
     */
    public final void setDelay(int delay) {
	scheduler.setDelay(delay);
    }

    /**
     * <html>Wait, Stop , Continue the Agent's life cycle Must be use in <b>@see
     * #live()</b> method</html>
     * @see setDelay()
     * @see pause()
     * @see stop()
     * @see resume()
     * @return if life cycle can proceed
     */
    public final boolean nextStep() {
	return scheduler.nextStep();
    }

    /**
     * launch an other agent synchroneous
     *
     * @param agt an agent from AgentLibrary
     */
    public final void launchAgent(AbstractAgent<?> agt) {
	launchAgent(agt, false);
    }

    /**
     * launch an other agent synchroneous
     *
     * @param agt the Agent to launch
     * @param async if it's asynchroneous or not
     */
    public final void launchAgent(AbstractAgent<?> agt, boolean async) {
	try {
	    agt.setGUI(gui);
	    if (async) {
		agt.start();
	    } else {
		agt.init();
		agt.initGUI();
		agt.activate();
		agt.live();
		agt.end();
		agt.kill();
	    }
	} catch (Exception er) {
	    er.printStackTrace();
	}
    }

    /**
     *
     * @param hashcode
     * @param mess
     */
    public final void sendMessage(int hashcode, Message<?> mess) {
	Message<?> m = mess.clone();
	m.setSender(this.hashcode);
	m.setReceiver(hashcode);
	AgentNode.getHandle().sendMessage(hashcode, m);
    }

    /**
     * Send a message to all Agents of a groupe
     *
     * @param groupe name for the groupe
     * @param mess message to send
     */
    public final void broadcastMessage(String groupe, Message<?> mess) {
	Message<?> m = mess.clone();
	m.setSender(this.hashcode);
	AgentNode.getHandle().broadcastMessage(groupe, null, m);
    }

    /**
     * Send a message to to all Agents of a group with role
     *
     * @param groupe name of the groupe
     * @param role name of the role
     * @param mess message to send
     */
    public final void broadcastMessage(String groupe, String role, Message<?> mess) {
	Message<?> m = mess.clone();
	m.setSender(this.hashcode);
	AgentNode.getHandle().broadcastMessage(groupe, role, m);
    }

    /**
     * wait until receive a message
     *
     * @return a message
     */
    public final Message<?> waitNextMessage() {
	return this.waitNextMessage(0);
    }

    /**
     * wait until receive a message or until time
     *
     * @param until time to wait
     * @return a message
     */
    public final Message<?> waitNextMessage(int until) {
	Message<?> mess = messages.pop(until);
	this.notifyMessagesChanged();
	return mess;
    }

    /**
     *
     * @param mes
     */
    public final void pushMessage(Message<?> mes) {
	messages.push(mes);
	this.notifyMessagesChanged();
    }

    /**
     *
     */
    public final void flushMessage() {
	messages.flush();
	this.notifyMessagesChanged();
    }

    /**
     * 
     */
    private void notifyMessagesChanged() {
	this.setChanged();
	AgentProbeValue<Integer> p = new AgentProbeValue<>("MESSAGES QUEUE", messages.size());
	this.notifyObservers(p);
    }

    /**
     *
     * @return messages queue's size
     */
    public final int getMessages() {
	return messages.size();
    }

    /**
     *
     * @param key
     * @param data
     * @return stored datas
     */
    public final T store_db(String key, T data) {
	return database.put(key, data);
    }

    /**
     *
     * @param key
     * @return datas
     */
    public final T get_db(String key) {
	return database.get(key);
    }

    /**
     * print the string
     *
     * @param str a string
     */
    public final void println(String str) {
	try {
	    if (hasGUI()) {
		try {
		    gui.write(str);
		} catch (Exception er) {
		    System.out.println(this.name + this.getHashcode() + ":" + str);
		}
	    } else {
		System.out.println(this.name + this.getHashcode() + ":" + str);
	    }
	} catch (NullPointerException e) {
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * join a group of agent
     *
     * @param grp name of the groupe
     */
    public final void joinGroupe(String grp) {
	address.addGroupe(grp);
	this.setChanged();
	AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("GROUPS ARRAY", address.getGroupes());
	this.notifyObservers(p);
    }

    /**
     * leave the groupe
     *
     * @param grp name of the groupe
     */
    public final void leaveGroupe(String grp) {
	address.removeGroupe(grp);
	this.setChanged();
	AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("GROUPS ARRAY", address.getGroupes());
	this.notifyObservers(p);
    }

    /**
     * Check if Agent's in a groupe
     *
     * @param grp name of the groupe
     * @return if Agent's in this groupe
     */
    public final boolean hasGroupe(String grp) {
	return address.hasGroupe(grp);
    }

    /**
     * Add Groupe with/out Role for Agent
     *
     * @param groupe name of the groupe
     * @param role name of the role
     */
    public final void addRole(String groupe, String role) {
	address.addRole(groupe, role);
	this.setChanged();
	AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("ROLES ARRAY", address.getRoles(groupe));
	this.notifyObservers(p);
    }

    /**
     * Remove Groupe with/out Role for Agent
     *
     * @param groupe name of the groupe
     * @param role name of the role
     */
    public final void removeRole(String groupe, String role) {
	address.removeRole(groupe, role);
	this.setChanged();
	AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("ROLES ARRAY", address.getRoles(groupe));
	this.notifyObservers(p);
    }

    /**
     * Check if agent is in a group and has role
     *
     * @param role name of the role
     * @param groupe name of the groupe
     * @return if Agent is in Groupe and Role
     */
    public final boolean hasRole(String groupe, String role) {
	return address.hasRole(groupe, role);
    }

    /**
     *
     * @param adr
     */
    public final void transfert(AgentAddress adr) {
    }

    /**
     *
     * @return
     */
    public final int getHashcode() {
	return hashcode;
    }

    /**
     * set the gui for the current agent
     *
     * @param gui
     */
    public final void setGUI(AgentGUI gui) {
	this.gui = gui;
    }

    /**
     * get the gui for the current agent
     *
     * @return AgentGUI
     */
    public final AgentGUI getGUI() {
	return gui;
    }

    /**
     * if the current agent has gor a gui
     *
     * @return true or false
     */
    public final boolean hasGUI() {
	if (gui == null) {
	    return false;
	} else {
	    if (gui.getFrame().isVisible()) {
		return true;
	    } else {
		return false;
	    }
	}
    }

    /**
     *
     * @param obs
     */
    public final void addProbe(Observer obs) {
	this.addObserver(obs);
    }

    /**
     *
     * @param obs
     */
    public final void removeProbe(Observer obs) {
	this.deleteObserver(obs);
    }

    /**
     *
     */
    public final void flushProbes() {
	this.deleteObservers();
    }

    /**
     *
     * @param agt
     */
    public void killAgent(AbstractAgent<?> agt) {
	agt.kill();
    }

    /**
     *
     * @param sensor
     */
    public final void addSensor(AgentSensor<?> sensor) {
	sensor.addObserver(this);
	this.sensors.add(sensor);

    }

    /**
     *
     * @param sensor
     */
    public final void removeSensor(AgentSensor<?> sensor) {
	sensor.deleteObserver(this);
	this.sensors.remove(sensor);
    }

    /**
     *
     */
    public final void flushSensors() {
	for (AgentSensor<?> sens : sensors) {
	    sens.deleteObserver(this);
	}
	this.sensors.removeAll(Arrays.asList(this.sensors.toArray()));
    }

    /**
     * Method to override if sensors want to be handled
     *
     * @param sensor sensor that trigger the event
     */
    protected void handleSensor(AgentSensor<?> sensor) {
	System.out.println(sensor.getValue().toString());
    }

    /**
     *
     * @param o observable that trigger the update event
     * @param arg
     * @throws ClassCastException
     */
    @Override
    public void update(Observable o, Object arg) throws ClassCastException {
	if (o instanceof AgentSensor<?>) {
	    handleSensor((AgentSensor<?>) o);
	} else {
	    throw new ClassCastException("Can't cast " + o.getClass() + " to " + AgentSensor.class);
	}
    }
}