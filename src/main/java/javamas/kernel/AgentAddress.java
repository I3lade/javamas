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

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentAddress.java
 *
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr> @version 1.0
 */
public final class AgentAddress implements Serializable {

    private static final long serialVersionUID = 201307011219L;
    private String id;

    /**
     *
     * @param hashcode
     */
    public AgentAddress(int hashcode) {
        id = "" + hashcode + "@" + AgentNode.getHandle().hashCode();
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
