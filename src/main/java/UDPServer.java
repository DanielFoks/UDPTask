import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


class UDPServer {

    private static final Logger log = Logger.getLogger(UDPServer.class);

    public static void main(String args[]) {
        DatagramSocket serverSocket = null;
        int port = 0;
        String sentence = null;
        InetAddress IPAddress = null;
        try {
            serverSocket = new DatagramSocket(14521);
            log.info("Socket was created");
        } catch (SocketException e) {
            log.error("Socket can not be created");
            log.error(e.getMessage());
        }
        byte[] receiveData;
        byte[] sendData = new byte[0];
        while (true) {
            receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData());
                log.info("Received message from client: \"" + sentence + "\"");
                IPAddress = receivePacket.getAddress();
                port = receivePacket.getPort();
                sendData = sentence.toUpperCase().getBytes();
            } catch (IOException e) {
                log.error("Packet can not be received");
                log.error(e.getMessage());
            }

            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
                log.info("Message: \"" + sentence.toUpperCase() + "\" was sent");
            } catch (IOException e) {
                log.error("Packet can not be sent");
                log.error(e.getMessage());
            }
        }
    }
}
