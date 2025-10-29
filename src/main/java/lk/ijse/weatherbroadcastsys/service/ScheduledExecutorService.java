package lk.ijse.weatherbroadcastsys.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ScheduledExecutorService {
    private Socket clientSocket;
    private DataOutputStream dOS;
    private DataInputStream dIS;

    public void startServer() {
        new Thread(() -> {
            while(true) {
                try (ServerSocket serverSocket = new ServerSocket(3000)) {
                    System.out.println("Server started on port 3000");
                    clientSocket = serverSocket.accept();
                    System.out.println("Client connected");
                } catch (Exception e) {
                    System.out.println("Server Error when starting");
                }
            }
        }).start();
    }
}
