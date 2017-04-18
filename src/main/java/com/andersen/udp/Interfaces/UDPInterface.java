package com.andersen.udp.Interfaces;

import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * This interface includes basic methods for working with the server and the client
 */
public interface UDPInterface {

    /**
     * @param port        Port for connection
     * @param inetAddress Address for connection
     * @return Socket for work
     */
    DatagramSocket createConnection(int port, InetAddress inetAddress);

    /**
     * @param message Message to be sent
     */
    void sendMessage(String message);

    /**
     * @return Message that was sent
     */
    String receiveMessage();

}
