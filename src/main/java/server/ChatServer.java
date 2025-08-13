package server;

import common.Message;
import lombok.Getter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static common.ChatConstants.*;

@Getter
public class ChatServer {
    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(START_PORT_MESSAGE + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            System.err.println(ERROR_MESSAGE + e.getMessage());
        }
    }

    public void broadcast(Message message) {
        String logEntry = String.format(MESSAGE_FORMAT,
                message.getTimestamp(), message.getSender(), message.getText());

        logToFile(logEntry);

        for (ClientHandler client : clients) {
            client.sendMessage(logEntry);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    private void logToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println(ERROR_MESSAGE + e.getMessage());
        }
    }
}
