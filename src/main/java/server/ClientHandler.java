package server;

import common.Message;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static common.ChatConstants.*;

@RequiredArgsConstructor
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ChatServer server;
    private PrintWriter out;
    private String username;

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            username = in.readLine();
            server.broadcast(new Message(username, JOIN_MESSAGE, getCurrentTime()));

            String input;
            while ((input = in.readLine()) != null && !input.equals(EXIT_COMMAND)) {
                server.broadcast(new Message(username, input, getCurrentTime()));
            }
        } catch (IOException e) {
            System.err.println(ERROR_MESSAGE + e.getMessage());
        } finally {
            server.removeClient(this);
            server.broadcast(new Message(username, EXIT_CHAT, getCurrentTime()));
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println(ERROR_MESSAGE + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }
}
