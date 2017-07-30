package com.andersen.udp;

import com.andersen.udp.Interfaces.UDPClientInterface;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;

/**
 * This class is UDP client.
 */
class UDPClient implements UDPClientInterface {

    /**
     * log4j logger.
     */
    private static final Logger log = Logger.getLogger(UDPClient.class);

    /**
     * Socket for receive and sent messages.
     */
    private final DatagramSocket clientSocket;

    /**
     * The server port for receive and sent messages.
     */
    private final int port;

    /**
     * The server address for receive and sent messages.
     */
    private final InetAddress inetAddress;

    public UDPClient(InetAddress inetAddress, int port) {

        DatagramSocket clientSocket1;

        try {
            clientSocket1 = new DatagramSocket();
            log.info("Socket was created");

        } catch (SocketException e) {
            clientSocket1 = null;
            log.error("Can not create socket: " + e.getMessage(), e);
        }

        this.clientSocket = clientSocket1;
        this.inetAddress = inetAddress;
        this.port = port;

    }

    /**
     * Sends message to server.
     *
     * @param message Message to be sent.
     * @return if message was sent. False if was not.
     * @throws IOException if message can not be sent.
     */
    @Override
    public boolean sendMessage(String message) {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, port);
        try {
            clientSocket.send(sendPacket);
            if (log.isDebugEnabled()) {
                log.debug("Message: \"" + message + "\"" + " was sent");
            }
            return true;
        } catch (IOException e) {
            log.error("Message can not be sent: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Receives packet from server.
     *
     * @return Message that was sent.
     * @throws IOException if message can not be received.
     */
    @Override
    public DatagramPacket receivePacket() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            clientSocket.receive(receivePacket);
            if (log.isDebugEnabled()) {
                log.debug("Packet: was received from " + receivePacket.getAddress());
            }
            return receivePacket;
        } catch (IOException e) {
            log.error("Message can not be received: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Get message from packet.
     *
     * @param datagramPacket The packet from which the message will be received.
     * @return Message from packet.
     */
    @Override
    public String getMessage(DatagramPacket datagramPacket) {
        String receivedMessage = null;
        if (datagramPacket != null) {
            receivedMessage = new String(datagramPacket.getData());
            if (log.isDebugEnabled()) {
                log.debug("Message: \"" + receivedMessage + "\"" + " was received from DatagramPacket");
            }
        }
        return receivedMessage;
    }

    @Override
    public void closeSocket() {

    }
}