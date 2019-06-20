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
package fr.eloane.javamas.kernel.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import fr.eloane.javamas.kernel.Node;
import fr.eloane.javamas.kernel.organization.Organization;

/**
 * Project: JavaMAS: Java Multi-Agents System File: Message.java
 *
 * @param <T>
 */
public class Message<T> extends HashMap<String, Object> implements Cloneable, Serializable, Comparable<Message<?>> {

    /**
     *
     */
    public static final String MESSAGE_ID = "message-id";
    /**
     *
     */
    public static final String IN_REPLY_TO = "in-reply-to";
    /**
     *
     */
    public static final String REPLY_BY = "reply_by";
    /**
     *
     */
    public static final String REPLY_TO = "reply-to";//Participant in communication
    /**
     *
     */
    public static final String LANGUAGE = "language";//Description of Content
    /**
     *
     */
    public static final String ENCODING = "encoding";//Description of Content
    /**
     *
     */
    public static final String ONTOLOGY = "ontology";//Description of Content
    /**
     *
     */
    public static final String PROTOCOL = "protocol";//Control of conversation  
    /**
     *
     */
    public static final String REPLY_WITH = "reply-with";//Control of conversation
    /**
     *
     */
    public static final String CONVERSATION_ID = "conversation_id";
    /**
     *
     */
    public static final String PRIORITY = "priority";
    /**
     *
     */
    public static final String EXPRIRE = "expire";
    /**
     *
     */
    public static final String CREATED = "created";
    /**
     *
     */
    public static final String SENDER = "sender";
    /**
     *
     */
    public static final String RECEIVERS = "receivers";
    /**
     *
     */
    public static final String RECEIVERS_ORGANIZATIONS = "receivers-organization";
    /**
     *
     */
    public static final int HIGH_PRIORITY = 1;
    /**
     *
     */
    public static final int LOW_PRIORITY = -1;
    /**
     *
     */
    public static final int NORMAL_PRIORITY = 0;
    /**
     *
     */
    public static final int EXTREM_PRIORITY = 2;
    private static AtomicInteger message_inc = new AtomicInteger();
    private static AtomicInteger conversation_inc = new AtomicInteger();
    private static final long serialVersionUID = -1322293292088397862L;

    /**
     *
     */
    protected T content = null;

    /**
     *
     */
    public Message() {
        this.put(Message.PRIORITY, NORMAL_PRIORITY);
        this.put(Message.CREATED, System.currentTimeMillis());
        this.put(Message.MESSAGE_ID, this.generateMessageId());
        this.put(Message.CONVERSATION_ID, this.generateConversationId());
    }

    /**
     *
     * @param content
     */
    public Message(T content) {
        this();
        this.setContent(content);
    }

    /**
     *
     * @param content
     * @param expire
     */
    public Message(T content, long expire) {
        this();
        this.setContent(content);
        this.put(Message.EXPRIRE, expire);
    }

    /**
     *
     * @param expire
     */
    public Message(long expire) {
        this();
        this.put(Message.EXPRIRE, expire);
    }

    /**
     *
     * @return
     */
    public final String getId() {
        return this.get(MESSAGE_ID).toString();
    }

    /**
     *
     * @return
     */
    public final T getContent() {
        return this.content;
    }

    /**
     *
     * @param content
     * @return
     */
    public final Message<T> setContent(T content) {
        this.content = content;
        return this;
    }

    /**
     * @param s
     * @return
     */
    public final Message<T> setSender(String s) {
        this.put(Message.SENDER, s);
        return this;
    }

    /**
     * Returns the original sender. This information can be trusted
     *
     * @return
     */
    public final String getSender() {
        return (String) this.get(Message.SENDER);
    }

    /**
     *
     * @param s
     * @return
     */
    public final Message<T> addReceiver(String s) {
        ArrayList<String> receivers = this.getReceivers();
        receivers.add(s);
        this.setReceivers(receivers);
        return this;
    }

    /**
     *
     * @param s
     * @return
     */
    public final Message<T> removeReceiver(String s) {
        ArrayList<String> receivers = this.getReceivers();
        receivers.remove(s);
        this.setReceivers(receivers);
        return this;
    }

    /**
     *
     * @param r
     * @return
     */
    public final Message<T> addReceivers(ArrayList<String> r) {
        ArrayList<String> receivers = this.getReceivers();
        receivers.addAll(r);
        this.setReceivers(receivers);
        return this;
    }

    /**
     *
     * @param r
     * @return
     */
    public final Message<T> setReceivers(ArrayList<String> r) {
        this.put(Message.RECEIVERS, r);
        return this;
    }

    /**
     * @return
     */
    public final ArrayList<String> getReceivers() {
        return (ArrayList<String>) this.getArray(Message.RECEIVERS);
    }

    /**
     *
     * @return
     */
    public final Message<T> removeReceivers() {
        this.remove(Message.RECEIVERS);
        return this;
    }

    /**
     *
     * @param t
     * @return
     */
    public final Message<T> addOrganization(Organization t) {
        ArrayList<Organization> organizations = this.getOrganizations();
        organizations.add(t);
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
        return this;
    }

    /**
     *
     * @param t
     * @return
     */
    public final Message<T> removeOrganization(Organization t) {
        ArrayList<Organization> organizations = this.getOrganizations();
        organizations.remove(t);
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
        return this;
    }

    /**
     *
     * @param t
     * @return
     */
    public final Message<T> setOrganization(Organization t) {
        ArrayList<Organization> organizations = new ArrayList<>();
        organizations.add(t);
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
        return this;
    }

    /**
     *
     * @param organizations
     * @return
     */
    public final Message<T> setOrganizations(ArrayList<Organization> organizations) {
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
        return this;
    }

    /**
     *
     * @return
     */
    public final Message<T> removeOrganizations() {
        this.remove(RECEIVERS_ORGANIZATIONS);
        return this;
    }

    /**
     *
     * @return
     */
    public final ArrayList<Organization> getOrganizations() {
        return (ArrayList<Organization>) this.getArray(Message.RECEIVERS_ORGANIZATIONS);
    }

    private ArrayList getArray(String type) {
        ArrayList array = (ArrayList) this.get(type);
        if (array == null) {
            array = new ArrayList<>();
        }
        return array;
    }

    /**
     *
     * @return
     */
    public final int getPriority() {
        return Integer.parseInt(this.get(Message.PRIORITY).toString());
    }

    /**
     *
     * @param priority
     */
    public final Message<T> setPriority(int priority) {
        this.put(Message.PRIORITY, priority);
        return this;
    }

    /**
     *
     * @return creation
     */
    public final long getTime() {
        return Long.parseLong(this.get(Message.CREATED).toString());
    }

    /**
     *
     * @return expire date
     */
    public final long getExpire() {
        return Long.parseLong(this.get(Message.EXPRIRE).toString());
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public final Message<T> setField(String key, Object value) {
        if (!key.equals(Message.CREATED) && !key.equals(Message.MESSAGE_ID)) {
            this.put(key, value);
        }
        return this;
    }

    /**
     * @return
     */
    @Override
    public Message<?> clone() {
        return (Message<?>) super.clone();
    }

    /**
     * Order by priority then by time
     *
     * @param mess
     * @return
     */
    @Override
    public int compareTo(Message<?> mess) {
        if (this.getPriority() < mess.getPriority()) {
            return 1;
        } else if (this.getPriority() > mess.getPriority()) {
            return -1;
        } else {
            if (this.getTime() < mess.getTime()) {
                return 1;
            } else if (this.getTime() > mess.getTime()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Message) {
            return this.get(MESSAGE_ID).equals(((Message) o).get(MESSAGE_ID));
        } else {
            return false;
        }
    }

    /**
     * Reply this message
     *
     * @return a new message with sender id as receiver and conversation id and
     * same fields
     */
    public Message<T> reply() {
        Message<T> reply = new Message<>();
        reply.addReceiver(this.getSender());
        reply.setField(Message.CONVERSATION_ID, this.get(Message.CONVERSATION_ID));
        return reply;
    }

    /**
     *
     * @return
     */
    private String generateConversationId() {
        return "C:" + Message.conversation_inc.getAndIncrement() + "@" + Node.getHandle().hashCode();
    }

    private String generateMessageId() {
        return "M:" + message_inc.getAndIncrement() + "@" + Node.getHandle().hashCode();
    }

    /**
     * Returns a debug string with enveloppe and content for the message
     *
     * @return message's content + other info
     */
    @Override
    public String toString() {
        String ret = "";
        ret = this.entrySet().stream().map((s) -> s.getKey() + ":" + s.getValue().toString().replace("\n", "") + "\n").reduce(ret, String::concat);
        ret += "Content:\n" + this.getContent().toString() + "\n";
        return ret;
    }
}
