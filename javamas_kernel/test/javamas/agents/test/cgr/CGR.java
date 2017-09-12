package javamas.agents.test.cgr;

import javamas.kernel.organization.OrganisationManager;

/**
 *
 * @author Guillaume Monet
 */
public class CGR {

    public static void main(String[] args) {
	OrganisationManager manager = new OrganisationManager();
	manager.addRole("WORLD", "BEEGROUP", "BEE");
	manager.addRole("WORLD", "BEEGROUP", "FOLLOWER");
	manager.addRole("WORLD", "QUEEN", "BEE");
	manager.addRole("WORLD", "QUEEN", "LEADER");
	
	System.out.println(manager);
    }
}
