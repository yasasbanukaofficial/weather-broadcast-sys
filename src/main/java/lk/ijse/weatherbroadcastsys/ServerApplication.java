package lk.ijse.weatherbroadcastsys;

import lk.ijse.weatherbroadcastsys.service.ScheduledExecutorService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
    public static void main(String[] args) {
        ScheduledExecutorService service = new ScheduledExecutorService();
        service.startServer();
    }
}
