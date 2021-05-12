package cn.iocoder.springboot.lab73.socket.test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class TimeClient {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {


            int port = 8087;
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                socket = new Socket("127.0.0.1", port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Query Time Order");
                System.out.println("Send order 2 server succeed.");
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
    }

    public void m1() {
        for (int i = 0; i < 10; i++) {


            int port = 8087;
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                socket = new Socket("127.0.0.1", port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Query Time Order " + i);
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("Query Time Order " + i);
                log.info("Send order 2 server succeed.");
                String resp = in.readLine();
                log.info("Now is : " + resp);
            } catch (IOException e){
                e.printStackTrace();
            } finally{
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
    }
}

