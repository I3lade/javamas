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
package javamas.agents.test.waiting;

import javamas.kernel.AbstractAgent;

/**
 *
 * @author Guillaume Monet
 */
public class Waiting extends AbstractAgent {

    public static void main(String[] args) {
	(new Waiting()).start();
    }

    @Override
    public void activate() {
	this.joinGroupe("WAITER");
    }

    @Override
    public void live() {
	this.println("WAITING FOR MESSAGE");
	this.waitNextMessage(5000);
	this.println("NO MORE WAITING");
    }

    @Override
    public void end() {
    }
}
