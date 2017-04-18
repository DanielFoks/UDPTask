package com.andersen.udp;

import com.andersen.udp.Interfaces.UDPClientInterface;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;

/**
 * This class is UDP client
 */
class UDPClient implements UDPClientInterface {

    /**
     * log4j logger
     */
    private static final Logger log = Logger.getLogger(UDPClient.class);

    /**
     * Socket for receive and sent messages
     */
    private DatagramSocket clientSocket;

    /**
     * The server port for receive and sent messages
     */
    private int port;

    /**
     * The server address for receive and sent messages
     */
    private InetAddress inetAddress;

    public UDPClient() {
    }

    /**
     * @param port        The server port for receive and sent messages
     * @param inetAddress The server address for receive and sent messages
     * @return Socket for receive and sent messages
     */
    @Override
    public DatagramSocket createConnection(int port, InetAddress inetAddress) {
        try {
            clientSocket = new DatagramSocket();
            this.port = port;
            this.inetAddress = inetAddress;
            log.info("Socket was created");
        } catch (SocketException e) {
            log.error("Can not create socket: " + e.getMessage(), e);
        }
        return clientSocket;
    }


    /**
     * @param message Message to be sent
     */
    @Override
    public void sendMessage(String message) {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, port);
        try {
            clientSocket.send(sendPacket);
            if (log.isDebugEnabled()) {
                log.debug("Message: \"" + message + "\"" + " was sent");
            }
        } catch (IOException e) {
            log.error("Message can not be sent: " + e.getMessage(), e);
        }
    }

    /**
     * @return Message that was sent
     */
    @Override
    public String receiveMessage() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String receivedMessage = null;
        try {
            clientSocket.receive(receivePacket);
            receivedMessage = new String(receivePacket.getData());
            if (log.isDebugEnabled()) {
                log.debug("Message: \"" + receivedMessage + "\"" + " was received from " + receivePacket.getAddress());
            }
        } catch (IOException e) {
            log.error("Message can not be received: " + e.getMessage(), e);
        }
        return receivedMessage;
    }
}