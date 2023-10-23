package com.eSonic.ecm;

import java.io.IOException;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.eSonic.ecm.util.TcpServer;


@Component
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
//        new Thread(() -> {
//            TcpServer tcpServer = new TcpServer();
//            try {
//                tcpServer.runServer();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }
}