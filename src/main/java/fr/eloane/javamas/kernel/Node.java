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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import fr.eloane.javamas.kernel.messages.Message;
import fr.eloane.javamas.kernel.transport.Transport;

/**
 *
 * @author Guillaume Monet
 */
public final class Node implements Observer {

    private static Node comm = null;
    private final HashMap<String, Agent<?>> agents = new HashMap<>();
    private final ArrayList<Transport> transports = new ArrayList<>();

    private Node() {
    }

    /**
     *
     * @param t
     */
    public void addTransport(Transport t) {
        transports.add(t);
        t.addObserver(this);
        t.start();
    }

    /**
     *
     * @param t
     */
    public void removeTransport(Transport t) {
        t.deleteObserver(this);
        transports.remove(t);
    }

    /**
     *
     * @return
     */
    public static Node getHandle() {
        if (comm == null) {
            comm = new Node();
            comm.start();
        }
        return comm;
    }

    /**
     *
     * @param agt
     */
    public synchronized void register(Agent<?> agt) {
        agents.put(agt.getAddress().getId(), agt);
    }

    /**
     *
     * @param agt
     */
    public synchronized void unregister(Agent<?> agt) {
        agents.remove(agt.getAddress().getId());
        if (agents.isEmpty()) {
            stop();
            Node.comm = null;
            System.out.println("Node Killed");
        }
    }

    /**
     *
     */
    public synchronized void pauseAll() {
        agents.values().forEach((a) -> {
            a.pause();
        });
    }

    /**
     *
     */
    public synchronized void resumeAll() {
        agents.values().forEach((a) -> {
            a.resume();
        });
    }

    /**
     *
     */
    public synchronized void stopAll() {
        agents.values().forEach((a) -> {
            a.stop();
        });
    }

    /**
     *
     * @param delay
     */
    public synchronized void setDelayAll(int delay) {
        agents.values().forEach((a) -> {
            a.setDelay(delay);
        });
    }

    /**
     *
     * @param id
     * @return
     */
    public Agent<?> getAgent(String id) {
        return agents.get(id);
    }

    /**
     *
     * @param mes
     */
    public synchronized void sendMessage(Message<?> mes) {
        if (mes.getReceivers().size() > 0) {
            mes.getReceivers().forEach((id) -> {
                if (agents.containsKey(id)) {
                    agents.get(id).pushMessage(mes);
                }
            });
        } 
        if (mes.getOrganizations().size() > 0) {
            mes.getOrganizations().forEach((organisation) -> {
                this.agents.values().stream().filter((agent) -> (agent.isInOrganization(organisation) && !agent.getAddress().getId().equals(mes.getSender()))).forEachOrdered((agent) -> {
                    agent.pushMessage(mes);
                });
            });
        }
        this.broadcastMessage(mes);
        //

        /*if (mes.getOrganizations() != null) {
            mes.getOrganizations().forEach((org) -> {
                agents.keySet().stream().filter((id) -> (!mes.getSender().equals(id))).forEachOrdered((id) -> {
                    org.getChilds().stream().filter((groups) -> (agents.get(id).hasGroupe(groups.getRoot()))).forEachOrdered((groups) -> {
                        if (!groups.isLeaf()) {
                            groups.getChilds().stream().filter((roles) -> (agents.get(id).hasRole(groups.getRoot(), roles.getRoot()))).forEachOrdered((_item) -> {
                                agents.get(id).pushMessage(mes);
                            });
                        } else {
                            agents.get(id).pushMessage(mes);
                        }
                    });
                });
            });
            broadcastMessage(mes);
        }*/
    }

    /**
     *
     * @param mes
     */
    private void broadcastMessage(Message<?> mes) {
        transports.forEach((t) -> {
            t.sendMessage(mes);
        });
    }

    /**
     *
     */
    public void start() {
        //(new Thread(this)).start();
    }

    /**
     *
     */
    public void stop() {
        this.kill();
    }

    /**
     *
     */
    private void kill() {
        transports.forEach((t) -> {
            t.close();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            this.sendMessage((Message) arg);
        }
    }

    @Override
    public int hashCode() {
        return (int) (Math.random() * 1000000);
    }

}
