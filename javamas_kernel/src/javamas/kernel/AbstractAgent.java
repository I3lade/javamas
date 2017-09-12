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
package javamas.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javamas.kernel.messages.Message;
import javamas.kernel.datas.SynchronizedPriority;
import javamas.kernel.datas.SynchronizedTree;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AbstractAgent.java
 *
 * @param <T> Class for the agent's inner database
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr> @version 1.5
 */
public abstract class AbstractAgent<T> extends Observable implements Serializable, Runnable, Observer {

    private AgentAddress address;
    private transient AgentGUI gui = null;
    private final ArrayList<AgentSensor<?>> sensors = new ArrayList<>();
    private final ArrayList<AgentProbe> probes = new ArrayList<>();
    private final SynchronizedPriority<Message<?>> messages = new SynchronizedPriority<>();
    private final AgentGRManager grmanager = new AgentGRManager();
    private final AgentScheduler scheduler = new AgentScheduler();
    private final AgentHistory history = new AgentHistory();
    private final AgentDatabase<T> database = null;
    private String name = "";
    private transient boolean daemon = false;
    private static final long serialVersionUID = 20130619L;
    public boolean killed = false;

    /**
     * Create new Agent
     */
    public AbstractAgent() {
        address = new AgentAddress(this.hashCode());
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
    protected abstract void end();

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
        this.killed = true;
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
     * Wait, Stop , Continue the Agent's life cycle<br />
     * Must be use in live() method
     *
     * @see setDelay()
     * @see pause()
     * @see stop()
     * @see resume()
     * @see live()
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
     * Send message to one agent
     *
     * @param mess
     * @param adr
     */
    public final void sendMessage(Message<?> mess, String adr) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        m.addReceiver(adr);
        AgentNode.getHandle().sendMessage(m);
    }

    /**
     * Send message to multiple agents
     *
     * @param mess
     * @param adrs
     */
    public final void sendMessage(Message<?> mess, ArrayList<String> adrs) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        m.setReceivers(adrs);
    }

    /*
	 public final Message sendMessageWaitReply(Message<?> mess, AgentAddress... adrs) {
	 for (AgentAddress adr : adrs) {
	 }
	 return null;
	 }*/
    /**
     * Send a message to all agents of a groupe
     *
     * @param group
     * @param mess message to send
     */
    public final void broadcastMessage(Message<?> mess, String group) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        SynchronizedTree<String> t = new SynchronizedTree<>(AgentGRManager.ROOT_ORGANIZATION);
        t.addNode(group);
        m.addOrganization(t);
        AgentNode.getHandle().sendMessage(m);
    }

    /**
     * Send a message to all agents of a group with role
     *
     * @param group
     * @param role name of the role
     * @param mess message to send
     */
    public final void broadcastMessage(Message<?> mess, String group, String role) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        SynchronizedTree<String> t = new SynchronizedTree<>(AgentGRManager.ROOT_ORGANIZATION);
        t.addNode(group).addNode(role);
        m.addOrganization(t);
        AgentNode.getHandle().sendMessage(m);
    }

    /**
     *
     * @param mess
     * @param t
     */
    public final void broadcastMessage(Message<?> mess, SynchronizedTree<String> t) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        m.addOrganization(t);
        AgentNode.getHandle().sendMessage(m);
    }

    /**
     *
     * @param mess
     * @param organizations
     */
    public final void broadcastMessage(Message<?> mess, ArrayList<SynchronizedTree<String>> organizations) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        for (SynchronizedTree<String> t : organizations) {
            m.addOrganization(t);
        }
        AgentNode.getHandle().sendMessage(m);
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
     * Add message to inner queue
     *
     * @param mes
     */
    public final void pushMessage(Message<?> mes) {
        messages.push(mes);
        history.add(mes);
        this.notifyMessagesChanged();
    }

    /**
     *
     * @param conditions
     * @return
     */
    public final ArrayList<Message> retreiveMessage(HashMap<String, String> conditions) {
        return history.getMessages(conditions);
    }

    /**
     * Remove all messages from queue
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
     * Return the messages queue's size
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
     * print something in current output or in the gui if is defined
     *
     * @param str something to print
     */
    public final void println(Object str) {
        try {
            if (this.hasGUI()) {
                try {
                    gui.write(str.toString());
                } catch (Exception er) {
                    System.out.println(this.name + this.address.toString() + ":" + str);
                }
            } else {
                System.out.println(this.name + this.address.toString() + ":" + str);
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * join a group
     *
     * @param grp name of the group
     */
    public final void joinGroupe(String grp) {
        grmanager.addGroupe(grp);
        this.setChanged();
        AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("GROUPS ARRAY", grmanager.getGroupes());
        this.notifyObservers(p);
    }

    /**
     * leave the groupe
     *
     * @param grp name of the group
     */
    public final void leaveGroupe(String grp) {
        grmanager.removeGroupe(grp);
        this.setChanged();
        AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("GROUPS ARRAY", grmanager.getGroupes());
        this.notifyObservers(p);
    }

    /**
     * Check if Agent's in a groupe
     *
     * @param grp name of the groupe
     * @return if Agent's in this groupe
     */
    public final boolean hasGroupe(String grp) {
        return grmanager.hasGroupe(grp);
    }

    /**
     * Add Groupe with/out Role for Agent
     *
     * @param groupe name of the groupe
     * @param role name of the role
     */
    public final void addRole(String groupe, String role) {
        grmanager.addRole(groupe, role);
        this.setChanged();
        AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("ROLES ARRAY", grmanager.getRoles(groupe));
        this.notifyObservers(p);
    }

    /**
     * Remove Groupe with/out Role for Agent
     *
     * @param groupe name of the groupe
     * @param role name of the role
     */
    public final void removeRole(String groupe, String role) {
        grmanager.removeRole(groupe, role);
        this.setChanged();
        AgentProbeValue<ArrayList<String>> p = new AgentProbeValue<>("ROLES ARRAY", grmanager.getRoles(groupe));
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
        return grmanager.hasRole(groupe, role);
    }

    /**
     *
     * @param adr
     */
    public final void transfert(AgentAddress adr) {
    }

    /**
     * Get the current AgentAddress
     *
     * @return
     */
    public final AgentAddress getAddress() {
        return address;
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
     * Add probe in the agent
     *
     * @param obs
     */
    public final void addProbe(AgentProbe obs) {
        this.addObserver(obs);
        this.probes.add(obs);
    }

    /**
     * Remove probe in the agent
     *
     * @param obs
     */
    public final void removeProbe(AgentProbe obs) {
        this.deleteObserver(obs);
        this.probes.remove(obs);
    }

    /**
     * Remove all probes
     */
    public final void flushProbes() {
        this.deleteObservers();
        this.probes.removeAll(this.probes);
    }

    /**
     * Return all probes
     *
     * @return
     */
    public final ArrayList<AgentProbe> getProbes() {
        return this.probes;
    }

    /**
     * Kill an agent
     *
     * @param agt
     */
    public final void killAgent(AbstractAgent<?> agt) {
        agt.kill();
    }

    /**
     * Add a sensor
     *
     * @param sensor
     */
    public final void addSensor(AgentSensor<?> sensor) {
        sensor.addObserver(this);
        this.sensors.add(sensor);

    }

    /**
     * Remove a sensor
     *
     * @param sensor
     */
    public final void removeSensor(AgentSensor<?> sensor) {
        sensor.deleteObserver(this);
        this.sensors.remove(sensor);
    }

    /**
     * Remove all the sensors
     */
    public final void flushSensors() {
        for (AgentSensor<?> sens : sensors) {
            sens.deleteObserver(this);
        }
        this.sensors.removeAll(this.sensors);
    }

    /**
     * Method to override if sensors want to be handled Handle trigger of the
     * sensor (pattern observer is used)
     *
     * @param sensor sensor that trigger the event
     */
    protected void handleSensor(AgentSensor<?> sensor) {
        System.out.println(sensor.getValue().toString());
    }

    /**
     * Handle update for observer pattern
     *
     * @param o observable that trigger the update event
     * @param arg
     * @throws ClassCastException
     */
    @Override
    public final void update(Observable o, Object arg) throws ClassCastException {
        if (o instanceof AgentSensor<?>) {
            handleSensor((AgentSensor<?>) o);
        } else {
            throw new ClassCastException("Can't cast " + o.getClass() + " to " + AgentSensor.class);
        }
    }
}
