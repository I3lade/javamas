package javamas.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentAddress.java
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
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr>
 * @version 1.0
 */
public final class AgentAddress extends HashMap<String, ArrayList<String>> implements Serializable {

    private int hashcode;
    private static final long serialVersionUID = 201307011219L;

    /**
     *
     * @param hashcode
     */
    public AgentAddress(int hashcode) {
	this.hashcode = hashcode;
    }

    /**
     *
     * @param groupe
     */
    public void addGroupe(String groupe) {
	this.put(groupe, new ArrayList<String>());
    }

    /**
     *
     * @param groupe
     * @param role
     */
    public void addGroupe(String groupe, String role) {
	this.addGroupe(groupe);
	this.addRole(groupe, role);
    }

    /**
     *
     * @param groupe
     */
    public void removeGroupe(String groupe) {
	this.remove(groupe);
    }

    /**
     *
     * @param groupe
     * @return
     */
    public boolean hasGroupe(String groupe) {
	return this.containsKey(groupe);
    }

    /**
     *
     * @param groupe
     * @param role
     */
    public void addRole(String groupe, String role) {
	this.get(groupe).add(role);
    }

    /**
     *
     * @param groupe
     * @param role
     */
    public void removeRole(String groupe, String role) {
	this.get(groupe).remove(role);
    }

    /**
     *
     * @param role
     * @param groupe
     * @return
     */
    public boolean hasRole(String groupe, String role) {
	if (this.hasGroupe(groupe) && this.get(groupe).contains(role)) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     *
     * @param groupe
     * @return
     */
    public ArrayList<String> getRoles(String groupe) {
	ArrayList<String> roles = new ArrayList<>();
	for (String role : this.get(groupe)) {
	    if (!roles.contains(role)) {
		roles.add(role);
	    }
	}
	return roles;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getGroupes() {
	return new ArrayList<>(this.keySet());
    }

    /**
     *
     * @return
     */
    public int getHashcode() {
	return hashcode;
    }
}
