package com.andersen.udp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPTest {


    @Before
    public void testConnectionServer() throws UnknownHostException {
        UDPServer udpServer = new UDPServer();
        udpServer.createConnection(14522, InetAddress.getByName("localhost"));
        udpServer.start();
    }

    @Test
    public void testReceivedMessage() {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {

            executor.execute(() -> {
                UDPClient udpClient = new UDPClient();
                try {
                    udpClient.createConnection(14522, InetAddress.getByName("localhost"));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                String message = String.valueOf((Math.random() * 100));

                udpClient.sendMessage(message);

                Assert.assertEquals(message, udpClient.receiveMessage().trim());
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }
}
