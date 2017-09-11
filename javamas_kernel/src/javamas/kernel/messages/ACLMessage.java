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
package javamas.kernel.messages;

import java.util.ArrayList;

/**
 * Original Work by the MaDKit Team : message send by Agents
 */
public final class ACLMessage extends Message<String> {

    private static final long serialVersionUID = 1L;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int ACCEPT_PROPOSAL = 0;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int AGREE = 1;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int CANCEL = 2;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int CFP = 3;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int CONFIRM = 4;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int DISCONFIRM = 5;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int FAILURE = 6;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int INFORM = 7;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int INFORM_IF = 8;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int INFORM_REF = 9;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int NOT_UNDERSTOOD = 10;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int PROPOSE = 11;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int QUERY_IF = 12;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int QUERY_REF = 13;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int REFUSE = 14;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int REJECT_PROPOSAL = 15;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int REQUEST = 16;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int REQUEST_WHEN = 17;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int REQUEST_WHENEVER = 18;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int SUBSCRIBE = 19;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int PROXY = 20;
    /**
     * constant identifying the FIPA performative *
     */
    public static final int PROPAGATE = 21;
    /**
     * constant identifying an unknown performative *
     */
    public static final int UNKNOWN = -1;
    /**
     *
     */
    public static final String ACCEPT_PROPOSAL_STRING = "ACCEPT-PROPOSAL";
    /**
     *
     */
    public static final String AGREE_STRING = "AGREE";
    /**
     *
     */
    public static final String CANCEL_STRING = "CANCEL";
    /**
     *
     */
    public static final String CFP_STRING = "CFP";
    /**
     *
     */
    public static final String CONFIRM_STRING = "CONFIRMP";
    /**
     *
     */
    public static final String DISCONFIRM_STRING = "DISCONFIRM";
    /**
     *
     */
    public static final String FAILURE_STRING = "FAILURE";
    /**
     *
     */
    public static final String INFORM_STRING = "INFORM";
    /**
     *
     */
    public static final String INFORM_IF_STRING = "INFORM-IF";
    /**
     *
     */
    public static final String INFORM_REF_STRING = "INFORM-REF";
    /**
     *
     */
    public static final String NOT_UNDERSTOOD_STRING = "NOT-UNDERSTOOD";
    /**
     *
     */
    public static final String PROPOSE_STRING = "PROPOSE";
    /**
     *
     */
    public static final String QUERY_IF_STRING = "QUERY-IF";
    /**
     *
     */
    public static final String QUERY_REF_STRING = "QUERY-REF";
    /**
     *
     */
    public static final String REFUSE_STRING = "REFUSE";
    /**
     *
     */
    public static final String REJECT_PROPOSAL_STRING = "REJECT-PROPOSAL";
    /**
     *
     */
    public static final String REQUEST_STRING = "REQUEST";
    /**
     *
     */
    public static final String REQUEST_WHEN_STRING = "REQUEST-WHEN";
    /**
     *
     */
    public static final String REQUEST_WHENEVER_STRING = "REQUEST-WHENEVER";
    /**
     *
     */
    public static final String SUBSCRIBE_STRING = "SUBSCRIBE";
    /**
     *
     */
    public static final String PROXY_STRING = "PROXY";
    /**
     *
     */
    public static final String PROPAGATE_STRING = "PROPAGATE";
    /**
     *
     */
    public static final ArrayList<String> performatives = new ArrayList<>();

    static {
        performatives.add("ACCEPT-PROPOSAL");
        performatives.add("AGREE");
        performatives.add("CANCEL");
        performatives.add("CFP");
        performatives.add("CONFIRM");
        performatives.add("DISCONFIRM");
        performatives.add("FAILURE");
        performatives.add("INFORM");
        performatives.add("INFORM-IF");
        performatives.add("INFORM-REF");
        performatives.add("NOT-UNDERSTOOD");
        performatives.add("PROPOSE");
        performatives.add("QUERY-IF");
        performatives.add("QUERY-REF");
        performatives.add("REFUSE");
        performatives.add("REJECT-PROPOSAL");
        performatives.add("REQUEST");
        performatives.add("REQUEST-WHEN");
        performatives.add("REQUEST-WHENEVER");
        performatives.add("SUBSCRIBE");
        performatives.add("PROXY");
        performatives.add("PROPAGATE");
    }
    private String action;

    /**
     * Default constructor for ACLMessage class
     */
    public ACLMessage() {
        action = "NOT_UNDERSTOOD_STRING";
        content = "NO_CONTENT";
    }

    /**
     * Constructor for ACLMessage class
     *
     * @param actType the performative
     */
    public ACLMessage(String actType) {
        action = actType.toUpperCase();
    }

    /**
     * Constructor for ACLMessage class
     *
     * @param actType the performative
     * @param cont the content of the message
     */
    public ACLMessage(String actType, String cont) {
        action = actType.toUpperCase();
        content = cont;
    }

    /**
     * Constructor for ACLMessage class
     *
     * @param perf a performative as an integer
     * @param cont the content of the message
     */
    public ACLMessage(int perf, String cont) {
        this.action = performatives.get(perf);
        this.content = cont;
    }

    /**
     * return the performative
     *
     * @return String
     */
    public String getAct() {
        return action;
    }

    /**
     * @return
     */
    public String getPerformative() {
        return getAct();
    }

    /**
     * @return
     */
    public String getValue() {
        return getContent();
    }

    /**
     * @param action
     */
    public void setPerformative(String action) {
        this.action = action;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "" + action + " " + content;
    }
}
