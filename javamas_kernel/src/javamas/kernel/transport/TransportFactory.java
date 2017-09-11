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
package javamas.kernel.transport;

import java.io.IOException;
import java.net.UnknownHostException;
import javamas.kernel.exception.UnknownTransport;

/**
 *
 * @author Guillaume Monet
 */
public abstract class TransportFactory {

    /**
     *
     */
    public static final int TRANSPORT_TCP = 1;
    /**
     *
     */
    public static final int TRANSPORT_UDP = 2;
    /**
     *
     */
    public static final int TRANSPORT_MULTICAST = 3;

    private TransportFactory() {
    }

    /**
     *
     * @param transport_type
     * @param ip
     * @param port
     * @return
     * @throws UnknownTransport
     * @throws UnknownHostException
     * @throws IOException
     */
    public static Transport getTransport(int transport_type, String ip, int port) throws UnknownTransport, UnknownHostException, IOException {
        switch (transport_type) {
            case TRANSPORT_TCP:
                return new TransportTCP(ip, port);
            case TRANSPORT_UDP:
                return new TransportUDP(ip, port);
            case TRANSPORT_MULTICAST:
                return new TransportMulticast(ip, port);
            default:
                throw new UnknownTransport();
        }
    }
}
