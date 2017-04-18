package com.andersen.udp;

import com.andersen.udp.Interfaces.UDPServerInterface;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class is UDP server
 */
class UDPServer extends Thread implements UDPServerInterface {

    /**
     * log4j logger
     */
    private static final Logger log = Logger.getLogger(UDPServer.class);

    /**
     * Socket for receive and sent messages
     */
    private DatagramSocket serverSocket;

    /**
     * Client port for sent messages and initial identify server port
     */
    private AtomicInteger port = new AtomicInteger();

    /**
     * Client address for sent messages and server address for receive messages
     */
    private InetAddress inetAddress;

    public UDPServer() {
    }

    /**
     * @param port        Initial identify server port
     * @param inetAddress Server address for receive messages
     * @return Socket for receive and sent messages
     */
    @Override
    public DatagramSocket createConnection(int port, InetAddress inetAddress) {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket(port);
            serverSocket = datagramSocket;
            this.port.set(port);
            this.inetAddress = inetAddress;
            log.info("Server was created");
        } catch (SocketException e) {
            log.error("Can not create socket: " + e.getMessage(), e);
        }
        return serverSocket;
    }


    /**
     * The server receives the message and sends it back
     */
    @Override
    public void run() {
        if (serverSocket != null) {
            while (true) {
                String sentence = receiveMessage();
                sendMessage(sentence);
            }
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
            serverSocket.receive(receivePacket);
            receivedMessage = new String(receivePacket.getData());
            port.set(receivePacket.getPort());

            if (log.isDebugEnabled()) {
                log.debug("Message: \"" + receivedMessage + "\"" + " was received from " + receivePacket.getAddress());
            }

        } catch (IOException e) {
            log.error("Message can not be received: " + e.getMessage(), e);
        }
        return receivedMessage;
    }

    /**
     * @param message Message to be sent
     */
    @Override
    public void sendMessage(String message) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, inetAddress, port.get());
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            log.error("Message can not be sent: " + e.getMessage(), e);
        }
    }
}