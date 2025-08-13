package client;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static common.ChatConstants.*;

@RequiredArgsConstructor
public class ChatClient {
    private final String host;
    private final int port;
    private final String username;

    public void start() {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner consoleInput = new Scanner(System.in)) {

            out.println(username);

            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        logToFile(serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println(ERROR_MESSAGE + e.getMessage());
                }
            }).start();

            String userInput;
            while (!(userInput = consoleInput.nextLine()).equals(EXIT_COMMAND)) {
                out.println(userInput);
            }
            System.out.println(EXIT_TERMINAL);
            System.exit(0);
        } catch (IOException e) {
            System.err.println(ERROR_MESSAGE + e.getMessage());
        }
    }

    void logToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println(ERROR_MESSAGE + e.getMessage());
        }
    }
}
