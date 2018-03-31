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
import java.util.Observable;
import java.util.Observer;

/**
 * Think about something hot because it'll getting cold
 *
 * @author Guillaume Monet
 */
public abstract class AgentProbe implements Serializable, Observer {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public final void update(Observable o, Object arg) {
        if (arg instanceof AgentProbeValue<?>) {
            this.handleProbe((AgentProbeValue<?>) arg);
        } else {
            this.handleProbe(arg);
        }
    }

    /**
     *
     * @param value
     */
    protected abstract void handleProbe(AgentProbeValue<?> value);

    /**
     *
     * @param value
     */
    private void handleProbe(Object value) {
        System.out.println(value.toString());
    }
}
