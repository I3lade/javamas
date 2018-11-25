/* 
 * The MIT License
 *
 * Copyright 2018 Guillaume Monet.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.eloane.javamas.kernel.transport;

import fr.eloane.javamas.kernel.exception.UnknownTransport;
import java.util.HashMap;

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
     * @param parameters
     * @return
     * @throws UnknownTransport
     */
    public static Transport getTransport(int transport_type, HashMap<String, String> parameters) throws UnknownTransport {
        switch (transport_type) {
            case TRANSPORT_TCP:
                return new TransportTCP(parameters);
            case TRANSPORT_UDP:
                return new TransportUDP(parameters);
            case TRANSPORT_MULTICAST:
                return new TransportMulticast(parameters);
            default:
                throw new UnknownTransport();
        }
    }

}
