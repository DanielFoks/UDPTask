package com.andersen.udp;

import com.andersen.udp.Interfaces.UDPServerInterface;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


/**
 * This class is UDP server.
 */
class UDPServer extends Thread implements UDPServerInterface {

    /**
     * log4j logger.
     */
    private static final Logger log = Logger.getLogger(UDPServer.class);

    /**
     * Socket for receive and sent messages.
     */
    private final DatagramSocket serverSocket;

    /**
     * Packet that was received from client.
     */
    private DatagramPacket receivedPacket;

    public UDPServer(int port) {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket(port);
            log.info("Server was created");
        } catch (SocketException e) {
            log.error("Can not create socket: " + e.getMessage(), e);
            datagramSocket = null;
        }

        this.serverSocket = datagramSocket;
    }


    /**
     * Receives message from client and send it to him.
     */
    @Override
    public void run() {
        if (serverSocket != null) {
            while (true) {
                String sentence = getMessage(receivePacket());
                sendMessage(sentence);
            }
        }
    }

    /**
     * Receives packet from client.
     *
     * @return Message that was sent.
     * @throws IOException if message can not be received.
     */
    @Override
    public DatagramPacket receivePacket() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            serverSocket.receive(receivePacket);

            if (log.isDebugEnabled()) {
                log.debug("Packet was received from " + receivePacket.getAddress());
            }

            this.receivedPacket = receivePacket;
            return receivePacket;

        } catch (IOException e) {
            log.error("Message can not be received: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Sends message to client.
     *
     * @param message Message to be sent.
     * @return if message was sent. False if was not.
     * @throws IOException if message can not be sent.
     */
    @Override
    public boolean sendMessage(String message) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, receivedPacket.getAddress(), receivedPacket.getPort());
            serverSocket.send(sendPacket);

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
}