import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;

class UDPClient {

    private static final Logger log = Logger.getLogger(UDPClient.class);

    private String message;

    public UDPClient(String sentence) {
        DatagramSocket clientSocket = null;
        InetAddress IPAddress = null;
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName("localhost");
            log.info("Socket was created");
        } catch (SocketException e) {
            log.error("Socket cat not be created");
            log.error(e.getMessage());
        } catch (UnknownHostException e) {
            log.error("The IP address of a host could not be determined");
            log.error(e.getMessage());
        }
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 14521);
        try {
            clientSocket.send(sendPacket);
            log.info("Message: \"" + sentence + "\" was sent");
        } catch (IOException e) {
            log.error("Packet can not be sent");
            log.error(e.getMessage());
        }
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData()).trim();
            message = modifiedSentence;
            log.info("Message: \"" + modifiedSentence + "\" was received");
        } catch (IOException e) {
            log.error("Packet can not be received");
            log.error(e.getMessage());
        }
        clientSocket.close();
    }

    public String getMessage() {
        return message;
    }
}