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
import javamas.kernel.datas.SynchronizedTree;

/**
 * FIRST LEVEL = ROOT NODE ORGANIZATION SEC LEVEL = GROUP ORGANIZATION THRID
 * LEVEL = ROLE ORGANIZATION
 *
 * @author Guillaume Monet
 */
public final class AgentGRManager extends SynchronizedTree<String> implements Serializable {

    /**
     *
     */
    public static final String ROOT_ORGANIZATION = "ORGANIZATION";

    /**
     *
     */
    public AgentGRManager() {
        super(ROOT_ORGANIZATION);
    }

    /**
     *
     * @param groupe
     */
    public void addGroupe(String groupe) {
        if (!this.contains(groupe)) {
            this.addNode(groupe);
        }
    }

    /**
     *
     * @param groupe
     * @param role
     */
    public void addRole(String groupe, String role) {
        if (!this.contains(groupe)) {
            this.addNode(groupe);
        }
        if (!this.getTree(groupe).contains(role)) {
            this.getTree(groupe).addNode(role);
        }
    }

    /**
     *
     * @param groupe
     */
    public void removeGroupe(String groupe) {
        this.removeNode(groupe);
    }

    /**
     *
     * @param groupe
     * @return
     */
    public boolean hasGroupe(String groupe) {
        return this.contains(groupe);
    }

    /**
     *
     * @param groupe
     * @param role
     */
    public void removeRole(String groupe, String role) {
        this.getTree(groupe).removeNode(role);
    }

    /**
     *
     * @param role
     * @param groupe
     * @return
     */
    public boolean hasRole(String groupe, String role) {
        return this.contains(groupe) && this.getTree(groupe).contains(role);
    }

    /**
     *
     * @param groupe
     * @return
     */
    public ArrayList<String> getRoles(String groupe) {
        return this.getTree(groupe).getChildsAsList();
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getGroupes() {
        return this.getChildsAsList();
    }
}
