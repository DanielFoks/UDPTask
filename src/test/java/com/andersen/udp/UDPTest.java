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
        UDPServer udpServer = new UDPServer(14522);
        udpServer.start();
    }

    @Test
    public void testReceivedMessage(){

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {

            executor.execute(() -> {
                UDPClient udpClient = null;
                try {
                    udpClient = new UDPClient(InetAddress.getByName("localhost"),14522);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                String message = String.valueOf((Math.random() * 100));

                assert udpClient != null;
                udpClient.sendMessage(message);

                Assert.assertEquals(message, udpClient.getMessage(udpClient.receivePacket()).trim());
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }
}
