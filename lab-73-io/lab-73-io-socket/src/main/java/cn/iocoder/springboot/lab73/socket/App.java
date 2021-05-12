package cn.iocoder.springboot.lab73.socket;

import cn.iocoder.springboot.lab73.socket.test.TimeClient;
import cn.iocoder.springboot.lab73.socket.test.TimeServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }


    @PostConstruct
    void test() {
        TimeServer timeServer = new TimeServer();
        TimeClient timeClient = new TimeClient();

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                timeServer.m2();
            }
        });

        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 1; i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int port = 8087;
                    Socket socket = null;
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try {
                        socket = new Socket("127.0.0.1", port);
                        log.info("new socket");
                        try {
                            Thread.sleep(10000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Query Time Order " + j);
                        log.info("Send order " + j + " server succeed.");
                        try {
                            Thread.sleep(200L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        out.println("Query Time Order " + j);
                        log.info("Send order " + j + " server succeed.");
                        String resp = in.readLine();
                        log.info("Now is : " + resp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        out.close();
                        out = null;
                    }
                }
            });
        }

    }
}
