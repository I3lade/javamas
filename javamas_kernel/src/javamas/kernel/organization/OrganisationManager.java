/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javamas.kernel.organization;

import javamas.kernel.datas.SynchronizedTree;

/**
 * Community Group Role manager
 *
 * @author Guillaume Monet
 */
public class OrganisationManager extends SynchronizedTree<String> {

    /**
     *
     * @param community
     */
    public void joinCommunity(String community) {
        this.addNode(community);
    }

    /**
     *
     * @param community
     * @param group
     */
    public void joinGroup(String community, String group) {
        this.addNode(community).addNode(group);
    }

    /**
     *
     * @param community
     * @param group
     * @param role
     */
    public void addRole(String community, String group, String role) {
        this.addNode(community).addNode(group).addNode(role);
    }

    /*public boolean hasCommunity(String community) {
	 }

	 public boolean hasGroup(String community, String group) {
	 }

	 public boolean hasRole(String community, String group, String role) {
	 return this.
	 }*/
}
