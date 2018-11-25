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
package fr.eloane.javamas.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import fr.eloane.javamas.kernel.messages.Message;
import fr.eloane.javamas.kernel.datas.SynchronizedPriority;
import fr.eloane.javamas.kernel.organization.Organization;

/**
 *
 * @author Guillaume Monet
 * @param <T>
 */
public abstract class Agent<T> extends Observable implements Serializable, Runnable, Observer {

    private static final long serialVersionUID = -3591756155645176746L;

    private Address address;
    private final ArrayList<Sensor<?>> sensors = new ArrayList<>();
    private final ArrayList<Probe> probes = new ArrayList<>();
    private final SynchronizedPriority<Message<?>> messages = new SynchronizedPriority<>();
    private final Organization grmanager = new Organization();
    private final Scheduler scheduler = new Scheduler();
    private final AgentHistory history = new AgentHistory();
    private final Database<T> database = null;
    private String name = "";
    private transient boolean daemon = false;
    private boolean killed = false;

    /**
     * Create new Agent
     */
    public Agent() {
        this.address = new Address();
        //database = new Database<T>(true);
        this.register();
    }

    /**
     * Create new Agent
     *
     * @param name set the current public name for the agent
     */
    public Agent(String name) {
        this();
        this.name = name;
    }

    /**
     * Create new Agent
     *
     * @param daemon set if the agent is a daemon
     */
    public Agent(boolean daemon) {
        this();
        this.daemon = daemon;
    }

    /**
     * Create new Agent
     *
     * @param name set the current public name for the agent
     * @param daemon set if the agent is a daemon
     */
    public Agent(String name, boolean daemon) {
        this();
        this.name = name;
        this.daemon = daemon;
    }

    /**
     * Register the Agent to the current AgentNode
     */
    private void register() {
        Node.getHandle().register(this);
    }

    /**
     * Unregister the Agent from the current AgentNode
     */
    private void unregister() {
        Node.getHandle().unregister(this);
    }

    /**
     * Life of the agent init initGUI activate live end kill private method
     * destroy all objects from the agents
     */
    @Override
    public final void run() {
        this.init();
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
        this.scheduler.stop();
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
    public final void launchAgent(Agent<?> agt) {
        launchAgent(agt, false);
    }

    /**
     * launch an other agent synchroneous
     *
     * @param agt the Agent to launch
     * @param async if it's asynchroneous or not
     */
    public final void launchAgent(Agent<?> agt, boolean async) {
        try {
            if (async) {
                agt.start();
            } else {
                agt.init();
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
     */
    public final void sendMessage(Message<?> mess) {
        Message<?> m = mess.clone();
        m.setSender(this.address.getId());
        Node.getHandle().sendMessage(m);
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
        AgentProbeValue<Integer> p = new AgentProbeValue<>("MESSAGES QUEUE", this.countMessages());
        this.notifyObservers(p);
    }

    /**
     * Return the messages queue's size
     *
     * @return messages queue's size
     */
    public final int countMessages() {
        return messages.size();
    }

    public Database getDatabase() {
        return this.database;
    }

    /**
     * print something in current output or in the gui if is defined
     *
     * @param str something to print
     */
    public final void println(Object str) {
        try {
            System.out.println(this.name + this.address.toString() + ":" + str);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final Organization getOrganization() {
        return this.grmanager;
    }

    public final void joinCommunity(String community) {
        this.grmanager.joinCommunity(community);
    }

    public final void leaveCommunity(String community) {
        this.grmanager.leaveCommunity(community);
    }

    public final boolean isInCommunity(String community) {
        return this.grmanager.isInCommunity(community);
    }

    public final void joinGroup(String community, String group) {
        this.grmanager.joinGroup(community, group);
    }

    public final void leaveGroup(String community, String group) {
        this.grmanager.leaveGroup(community, group);
    }

    public final boolean isInGroup(String community, String group) {
        return this.grmanager.isInGroup(community, group);
    }

    public final void addRole(String community, String group, String role) {
        this.grmanager.addRole(community, group, role);
        this.setChanged();
    }

    public final void removeRole(String community, String group, String role) {
        this.grmanager.removeRole(community, group, role);
    }

    public final boolean hasRole(String community, String group, String role) {
        return this.grmanager.hasRole(community, group, role);
    }

    public final boolean isInOrganization(Organization organization) {
        return this.grmanager.compare(organization);
    }

    /**
     * Get the current AgentAddress
     *
     * @return
     */
    public final Address getAddress() {
        return address;
    }

    /**
     * Add probe in the agent
     *
     * @param obs
     */
    public final void addProbe(Probe obs) {
        this.addObserver(obs);
        this.probes.add(obs);
    }

    /**
     * Remove probe in the agent
     *
     * @param obs
     */
    public final void removeProbe(Probe obs) {
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
    public final ArrayList<Probe> getProbes() {
        return this.probes;
    }

    /**
     * Kill an agent
     *
     * @param agt
     */
    public final void killAgent(Agent<?> agt) {
        agt.kill();
    }

    /**
     * Add a sensor
     *
     * @param sensor
     */
    public final void addSensor(Sensor<?> sensor) {
        sensor.addObserver(this);
        this.sensors.add(sensor);

    }

    /**
     * Remove a sensor
     *
     * @param sensor
     */
    public final void removeSensor(Sensor<?> sensor) {
        sensor.deleteObserver(this);
        this.sensors.remove(sensor);
    }

    /**
     * Remove all the sensors
     */
    public final void flushSensors() {
        sensors.forEach((sens) -> {
            sens.deleteObserver(this);
        });
        this.sensors.removeAll(this.sensors);
    }

    /**
     * Method to override if sensors want to be handled Handle trigger of the
     * sensor (pattern observer is used)
     *
     * @param sensor sensor that trigger the event
     */
    protected void handleSensor(Sensor<?> sensor) {
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
        if (o instanceof Sensor<?>) {
            handleSensor((Sensor<?>) o);
        } else {
            throw new ClassCastException("Can't cast " + o.getClass() + " to " + Sensor.class);
        }
    }
}
