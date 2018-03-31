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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javamas.kernel.messages.Message;

/**
 * Knowledge of the agent : Messages history
 *
 * @author Guillaume Monet
 */
public class AgentHistory extends ArrayList<Message<?>> {

    /**
     *
     * @param conditions
     * @return
     */
    public ArrayList<Message> getMessages(HashMap<String, String> conditions) {
        ArrayList<Message> ret = new ArrayList<>();
        for (Message mes : this) {
            boolean ok = false;
            for (Entry ent : conditions.entrySet()) {
                if (mes.get(ent.getKey()) != null && mes.get(ent.getKey()).equals(ent.getValue())) {
                    ok = true;
                } else {
                    break;
                }
            }
            if (ok) {
                ret.add(mes.clone());
            }
        }
        return ret;
    }

    /**
     *
     * @param mes
     */
    public void putMessage(Message mes) {
        this.add(mes.clone());
    }
}
