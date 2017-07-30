package com.andersen.udp.Interfaces;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Methods for working with the server and the client.
 */
public interface UDPInterface {

    /**
     * Sends message to server or client.
     *
     * @param message Message to be sent.
     * @return if message was sent. False if was not.
     * @throws IOException if message can not be sent.
     */
    boolean sendMessage(String message);

    /**
     * Receives packet from server or client.
     *
     * @return Message that was sent.
     * @throws IOException if message can not be received.
     */
    DatagramPacket receivePacket();

    /**
     * Get message from packet.
     *
     * @param datagramPacket The packet from which the message will be received.
     * @return Message from packet.
     */
    String getMessage(DatagramPacket datagramPacket);


    void closeSocket();

}
